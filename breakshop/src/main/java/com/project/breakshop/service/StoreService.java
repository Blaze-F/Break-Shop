package com.project.breakshop.service;
import com.project.breakshop.exception.StoreNameAlreadyExistException;
import com.project.breakshop.models.DTO.OrderDTO;
import com.project.breakshop.models.DTO.OrderDTO.OrderStatus;
import com.project.breakshop.models.DTO.OrderReceiptDTO;
import com.project.breakshop.models.DTO.PushMessageDTO;
import com.project.breakshop.models.DTO.StoreDTO;
import com.project.breakshop.models.entity.Order;
import com.project.breakshop.models.entity.Store;
import com.project.breakshop.models.repository.OrderRepository;
import com.project.breakshop.models.repository.StoreRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Caching;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.HttpClientErrorException;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class StoreService {

    @Autowired
    public StoreService(StoreRepository storeRepository, OrderRepository orderRepository, DeliveryService deliveryService, RiderService riderService, ModelMapper modelMapper){
        this.storeRepository = storeRepository;
        this.orderRepository = orderRepository;
        this.deliveryService = deliveryService;
        this.riderService = riderService;
        this.modelMapper = modelMapper;
    }
    private final StoreRepository storeRepository;
    private final OrderRepository orderRepository;
    private final DeliveryService deliveryService;
    private final RiderService riderService;
    private final ModelMapper modelMapper;


    @Caching(evict = {
        @CacheEvict(value = "stores", key = "#store.categoryId"),
        @CacheEvict(value = "stores", key = "#store.address"),
        @CacheEvict(value = "stores", key = "#store.address+#store.categoryId")
    })
    public void insertStore(StoreDTO store, Long ownerId) {
        try {
            StoreDTO newStore = setOwnerID(store, ownerId);
            storeRepository.save(modelMapper.map(newStore, Store.class));
        } catch (DuplicateKeyException e) {
            throw new StoreNameAlreadyExistException("Same Store Name" + store.getName());
        }
    }

    private StoreDTO setOwnerID(StoreDTO store, Long ownerId) {
        return StoreDTO.builder()
            .name(store.getName())
            .phone(store.getPhone())
            .address(store.getAddress())
            .ownerId(ownerId)
            .introduction(store.getIntroduction())
            .categoryId(store.getCategoryId())
            .build();
    }

    public List<StoreDTO> findMyAllStore(String email) {
        List<Store> storeList = storeRepository.findByUserEmail(email);
        return storeList.stream().map(e -> modelMapper.map(e, StoreDTO.class)).collect(Collectors.toList());
    }

    public StoreDTO getMyStore(long userId, long storeId) {
        Store store =  storeRepository.getByUserIdAndId(userId , storeId).get();
        return modelMapper.map(store, StoreDTO.class);
    }

    private boolean isMyStore(long storeId, long ownerId) {
        return storeRepository.existsByIdAndUserId(storeId, ownerId);
    }

    public void closeMyStore(long storeId) {
        /* 현재 상점의 상태를 닫음으로 설정합니다. */
        storeRepository.closeMyStore(storeId);
    }

    public void openMyStore(long storeId) {
        storeRepository.openMyStore(storeId);
    }

    public void validateMyStore(long storeId, long ownerId) throws HttpClientErrorException {
        boolean isMyStore = isMyStore(storeId, ownerId);
        if (!isMyStore) {
            throw new HttpClientErrorException(HttpStatus.UNAUTHORIZED);
        }
    }

    @Transactional
    public void approveOrder(long orderId) {
        Order orderEntity = orderRepository.findById(orderId).get();
        OrderDTO orderDTO = modelMapper.map(orderEntity, OrderDTO.class);
        orderDTO.setOrderStatus(OrderStatus.APPROVED_ORDER);
        orderRepository.save(modelMapper.map(orderDTO, Order.class));


        OrderReceiptDTO orderReceipt = orderRepository.selectOrderReceipt(orderId);
        deliveryService.registerStandbyOrderWhenOrderApprove(orderId, orderReceipt);
        riderService.sendMessageToStandbyRidersInSameArea(orderReceipt.getStoreInfo().getAddress(),
            getPushMessage(orderReceipt));
    }

    private PushMessageDTO getPushMessage(OrderReceiptDTO orderReceipt) {
        return PushMessageDTO.builder()
            .title(PushMessageDTO.RIDER_MESSAGE_TITLE)
            .content(PushMessageDTO.RIDER_MESSAGE_TITLE)
            .createdAt(LocalDateTime.now().toString())
            .orderReceipt(orderReceipt)
            .build();

    }

}
