package com.project.breakshop.service;
import com.project.breakshop.models.DAO.CartItemDAO;
import com.project.breakshop.models.DTO.*;
import com.project.breakshop.models.entity.Order;
import com.project.breakshop.models.entity.Store;
import com.project.breakshop.models.entity.User;
import com.project.breakshop.models.repository.OrderRepository;
import com.project.breakshop.models.repository.StoreRepository;
import com.project.breakshop.models.repository.UserRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.support.TransactionSynchronization;
import org.springframework.transaction.support.TransactionSynchronizationAdapter;
import org.springframework.transaction.support.TransactionSynchronizationManager;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class OrderService {

    @Autowired
    public OrderService(OrderRepository orderRepository, UserRepository userRepository, OrderTransactionService orderTransactionService, CartItemDAO cartItemDAO, StoreRepository storeRepository, ModelMapper modelMapper ){
        this.orderRepository = orderRepository;
        this.userRepository = userRepository;
        this.orderTransactionService = orderTransactionService;
        this.cartItemDAO = cartItemDAO;
        this.storeRepository = storeRepository;
        this.modelMapper = modelMapper;
    }
    private final OrderRepository orderRepository;
    private final UserRepository userRepository;
    private final OrderTransactionService orderTransactionService;
    private final CartItemDAO cartItemDAO;
    private final StoreRepository storeRepository;
    private final ModelMapper modelMapper;


    @Transactional(rollbackFor = Exception.class)
    public OrderReceiptDTO registerOrder(String userId, long storeId, PayDTO.PayType payType) {
        // 사용자 엔티티를 가져와서 UserInfoDTO로 변환
        User userEntity = userRepository.findById(Long.parseLong(userId))
                .orElseThrow(() -> new RuntimeException("사용자를 찾을 수 없습니다"));
        UserInfoDTO userInfoDTO = modelMapper.map(userEntity, UserInfoDTO.class);

        // 상점 엔티티를 가져옴
        Store store = storeRepository.findById(storeId)
                .orElseThrow(() -> new RuntimeException("상점을 찾을 수 없습니다"));

        // OrderDTO를 생성하고 장바구니 항목을 가져옴
        OrderDTO orderDTO = getOrderDTO(userInfoDTO, storeId);
        List<CartItemDTO> cartList = cartItemDAO.getCartAndDelete(userId);
        List<OrderMenuDTO> orderMenuList = new ArrayList<>();
        List<OrderMenuOptionDTO> orderMenuOptionList = new ArrayList<>();
        OrderReceiptDTO orderReceipt;

        try {
            // Order 엔티티를 먼저 생성
            Order beforePaidOrder = Order.builder()
                    .orderStatus(Order.OrderStatus.BEFORE_ORDER)
                    .address(orderDTO.getAddress())
                    .totalPrice(0L) // 현재는 가격이 0으로 설정
                    .user(userEntity)
                    .store(store)
                    .build();

            beforePaidOrder = orderRepository.save(beforePaidOrder);

            //DTO 에 ID 정보 저장
            orderDTO.setId(beforePaidOrder.getId());

            // 총 가격 계산
            long totalPrice = orderTransactionService.order(orderDTO, cartList, orderMenuList, orderMenuOptionList);
            orderDTO.setTotalPrice(totalPrice);

            // 결제 진행
            orderTransactionService.pay(payType, totalPrice, orderDTO.getId());

            // 주문 상태 업데이트
            orderDTO.setOrderStatus(OrderDTO.OrderStatus.COMPLETE_ORDER);

            Order updatedOrder = modelMapper.map(orderDTO, Order.class);
            // 모든 변경 사항을 한 번에 저장
            orderRepository.save(updatedOrder);

            // 영수증 생성
            orderReceipt = getOrderReceipt(orderDTO, cartList, totalPrice, storeId, userInfoDTO);

            return orderReceipt;
        } catch (Exception e) {
            // 실패 시 장바구니 변경 롤백
            cartItemDAO.insertMenuList(userId, cartList);
            throw e; // 트랜잭션 롤백을 위해 예외를 다시 발생
        }
    }





    private OrderDTO getOrderDTO(UserInfoDTO userInfo, long storeId) {
        return OrderDTO.builder()
                .id(1L)
            .address(userInfo.getAddress())
            .userId(userInfo.getId())
            .orderStatus(OrderDTO.OrderStatus.BEFORE_ORDER)
            .storeId(storeId)
            .build();
    }

    private OrderReceiptDTO getOrderReceipt(OrderDTO orderDTO, List<CartItemDTO> cartList,
                                            long totalPrice, long storeId, UserInfoDTO userInfo) {

        // Store 객체를 가져온 후 StoreInfoDTO로 매핑
        Store store = storeRepository.findById(storeId)
                .orElseThrow(() -> new RuntimeException("Store not found"));

        StoreInfoDTO storeInfo = StoreInfoDTO.builder()
                .storeId(store.getId())
                .name(store.getName())
                .phone(store.getPhone())
                .address(store.getAddress())
                .build();

        // OrderReceiptDTO를 빌더 패턴으로 생성
        return OrderReceiptDTO.builder()
                .orderId(orderDTO.getId())
                .orderStatus(OrderDTO.OrderStatus.COMPLETE_ORDER.toString())
                .userInfo(userInfo)
                .totalPrice(totalPrice)
                .storeInfo(storeInfo)
                .cartList(cartList)
                .build();
    }

    public OrderReceiptDTO getOrderInfoByOrderId(long orderId) {
        Order order = orderRepository.findById(orderId).get();
        return modelMapper.map(order, OrderReceiptDTO.class);
    }

    public List<OrderStoreDetailDTO> getStoreOrderInfoByStoreId(long storeId) {
        return orderRepository.findByStoreId(storeId).stream().map(e -> modelMapper.map(e, OrderStoreDetailDTO.class)).collect(Collectors.toList());
    }

}