package com.project.breakshop.service;

import com.project.breakshop.exception.StoreNameAlreadyExistException;
import com.project.breakshop.models.DTO.OrderDTO;
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


/**
 * 컨벤션 : 부모, 자식 엔티티 매핑은 빌더패턴으로 하는 것으로. 부모 자식간 엔티티 매핑이 필요 없을경우는 modelMapper 사용.
 */
@Service
public class StoreService {

    @Autowired
    public StoreService(StoreRepository storeRepository, OrderRepository orderRepository, DeliveryService deliveryService, RiderService riderService,
                         ModelMapper modelMapper, UserRepository userRepository, StoreCategoryRepository storeCategoryRepository){
        this.storeRepository = storeRepository;
        this.orderRepository = orderRepository;
        this.deliveryService = deliveryService;
        this.riderService = riderService;
        this.modelMapper = modelMapper;
        this.userRepository = userRepository;
        this.storeCategoryRepository = storeCategoryRepository;
    }
    private final StoreRepository storeRepository;
    private final OrderRepository orderRepository;
    private final DeliveryService deliveryService;
    private final RiderService riderService;
    private final ModelMapper modelMapper;
    private final UserRepository userRepository;
    private final StoreCategoryRepository storeCategoryRepository;



    @Caching(evict = {
        @CacheEvict(value = "stores", key = "#store.categoryId"),
        @CacheEvict(value = "stores", key = "#store.address"),
        @CacheEvict(value = "stores", key = "#store.address+#store.categoryId")
    })
    public void insertStore(CreateStoreRequest storeCreateRequest, String ownerEmail) {
        try {
            Optional<User> userOptional = userRepository.getByEmail(ownerEmail);
            if (userOptional.isEmpty()){
                throw new UsernameNotFoundException("유저가 존재하지 않습니다.");
            }
            User userEntity = userOptional.get();

            //카테고리가 존재하지 않을경우 새로운 카테고리를 생성
            if (!storeCategoryRepository.existsByName(storeCreateRequest.getCategoryName())){
                StoreCategory storeCategory = StoreCategory.builder()
                        .name(storeCreateRequest.getCategoryName())
                        .build();
                storeCategoryRepository.save(storeCategory);
            }
            StoreCategory storeCategory = storeCategoryRepository.getByName(storeCreateRequest.getCategoryName()).get();
            Store storeEntity = Store.builder()
                    .storeCategory(storeCategory)
                    .address(storeCreateRequest.getAddress())
                    .introduction(storeCreateRequest.getIntroduction())
                    .phone(storeCreateRequest.getPhone())
                    .name(storeCreateRequest.getName())
                    .introduction(storeCreateRequest.getIntroduction())
                    .openStatus(Store.OpenStatus.CLOSED)
                    .user(userEntity)
                    .build();
            storeRepository.save(storeEntity);
        } catch (DuplicateKeyException e) {
            throw new StoreNameAlreadyExistException("Same Store Name" + storeCreateRequest.getName());
        }
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
        orderRepository.approveOrder(Order.OrderStatus.APPROVED_ORDER, orderEntity.getId());

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
