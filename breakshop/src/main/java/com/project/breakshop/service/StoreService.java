package com.project.breakshop.service;
import com.project.breakshop.exception.StoreNameAlreadyExistException;
import com.project.breakshop.models.DTO.OrderDTO;
import com.project.breakshop.models.DTO.OrderDTO.OrderStatus;
import com.project.breakshop.models.DTO.OrderReceiptDTO;
import com.project.breakshop.models.DTO.PushMessageDTO;
import com.project.breakshop.models.DTO.StoreDTO;
import com.project.breakshop.models.DTO.requests.CreateStoreRequest;
import com.project.breakshop.models.entity.Order;
import com.project.breakshop.models.entity.Store;
import com.project.breakshop.models.entity.StoreCategory;
import com.project.breakshop.models.entity.User;
import com.project.breakshop.models.repository.OrderRepository;
import com.project.breakshop.models.repository.StoreCategoryRepository;
import com.project.breakshop.models.repository.StoreRepository;
import com.project.breakshop.models.repository.UserRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Caching;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.HttpClientErrorException;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class StoreService {

    @Autowired
    public StoreService(StoreRepository storeRepository, OrderRepository orderRepository, DeliveryService deliveryService, RiderService riderService,
                        UserService userService, ModelMapper modelMapper, UserRepository userRepository, StoreCategoryRepository storeCategoryRepository){
        this.storeRepository = storeRepository;
        this.orderRepository = orderRepository;
        this.deliveryService = deliveryService;
        this.riderService = riderService;
        this.userService = userService;
        this.modelMapper = modelMapper;
        this.userRepository = userRepository;
        this.storeCategoryRepository = storeCategoryRepository;
    }
    private final StoreRepository storeRepository;
    private final OrderRepository orderRepository;
    private final DeliveryService deliveryService;
    private final RiderService riderService;
    private final UserService userService;
    private final ModelMapper modelMapper;
    private final UserRepository userRepository;
    private final StoreCategoryRepository storeCategoryRepository;



    @Caching(evict = {
        @CacheEvict(value = "stores", key = "#store.categoryId"),
        @CacheEvict(value = "stores", key = "#store.address"),
        @CacheEvict(value = "stores", key = "#store.address+#store.categoryId")
    })
    public void insertStore(CreateStoreRequest store, String ownerEmail) {
        try {
            Optional<User> userOptional = userRepository.getByEmail(ownerEmail);
            if (userOptional.isEmpty()){
                throw new UsernameNotFoundException("유저가 존재하지 않습니다.");
            }
            User userEntity = userOptional.get();

            StoreCategory storeCategory = StoreCategory.builder().id(store.getCategoryId()).build();
            //카테고리가 존재하지 않을경우 새로운 카테고리를 생성
            if (!storeCategoryRepository.existsById(store.getCategoryId())){
                storeCategoryRepository.save(storeCategory);
            }

            StoreDTO newStore = setOwnerID(store, userEntity.getId());
            Store storeEntity = modelMapper.map(newStore, Store.class);
            storeEntity.setUser(userEntity);
            storeEntity.setStoreCategory(storeCategory);
            storeRepository.save(storeEntity);
        } catch (DuplicateKeyException e) {
            throw new StoreNameAlreadyExistException("Same Store Name" + store.getName());
        }
    }

    private StoreDTO setOwnerID(CreateStoreRequest store, Long ownerId) {
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

    public StoreDTO getMyStore(long storeId, String userEmail) {
        Store store =  storeRepository.getByUserEmailAndId(userEmail , storeId).get();
        return modelMapper.map(store, StoreDTO.class);
    }

    private boolean isMyStore(long storeId, String ownerEmail) {
        return storeRepository.existsByIdAndUserEmail(storeId, ownerEmail);
    }

    public void closeMyStore(long storeId) {
        /* 현재 상점의 상태를 닫음으로 설정합니다. */
        storeRepository.closeMyStore(storeId);
    }

    public void openMyStore(long storeId) {
        storeRepository.openMyStore(storeId);
    }

    public void validateMyStore(long storeId, String ownerEmail) throws HttpClientErrorException {
        boolean isMyStore = isMyStore(storeId, ownerEmail);
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
