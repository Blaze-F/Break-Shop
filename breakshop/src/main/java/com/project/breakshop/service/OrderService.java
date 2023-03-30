package com.project.breakshop.service;
import com.project.breakshop.DAO.CartItemDAO;
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

    @Transactional
    public OrderReceiptDTO registerOrder(String userId, long storeId, PayDTO.PayType payType) {

        User userEntity = userRepository.findById(Long.parseLong(userId)).get();
        UserInfoDTO user = modelMapper.map(userEntity, UserInfoDTO.class);
        Store store = storeRepository.findById(storeId).get();
        OrderDTO orderDTO = getOrderDTO(user, storeId);
        List<CartItemDTO> cartList;
        List<OrderMenuDTO> orderMenuList = new ArrayList<>();
        List<OrderMenuOptionDTO> orderMenuOptionList = new ArrayList<>();
        OrderReceiptDTO orderReceipt;

        cartList = cartItemDAO.getCartAndDelete(userId);

        //오더 캔슬시 카트 아이템을 롤백하는 로직
        restoreCartListOnOrderRollback(userId, cartList);

        long totalPrice = orderTransactionService
            .order(orderDTO, cartList, orderMenuList, orderMenuOptionList);
        orderTransactionService.pay(payType, totalPrice, orderDTO.getId());

        orderDTO.setOrderStatus(OrderDTO.OrderStatus.COMPLETE_ORDER);
        //TODO 이부분도 USER 엔티티 부분 제대로 매핑되었는지 확인해야할듯
        //TODO Redis Cluster 부분 해결되어야 제대로 작동될 로직임.
        //TODO payments 옵션 어디로 가는지 나중에 확인 바람.
        Order order = Order.builder()
                .orderStatus(Order.OrderStatus.APPROVED_ORDER)
                .address(orderDTO.getAddress())
                //.orderMenuOptions()
                .totalPrice(totalPrice)
                .user(userEntity)
                .address(orderDTO.getAddress())
                .store(store)
                .build();
        orderRepository.save(order);

        orderReceipt = getOrderReceipt(orderDTO, cartList, totalPrice, storeId,
            user);

        return orderReceipt;
    }
    private void restoreCartListOnOrderRollback(String userId, List<CartItemDTO> cartList) {
        TransactionSynchronizationManager.registerSynchronization(
                //TODO Deprecated
            new TransactionSynchronizationAdapter() {
                @Override
                public void afterCompletion(int status) {
                    if (status == STATUS_ROLLED_BACK) {
                        cartItemDAO.insertMenuList(userId, cartList);
                    }
                }
            });
    }

    private OrderDTO getOrderDTO(UserInfoDTO userInfo, long storeId) {
        return OrderDTO.builder()
            .address(userInfo.getAddress())
            .userId(userInfo.getId())
            .orderStatus(OrderDTO.OrderStatus.BEFORE_ORDER)
            .storeId(storeId)
            .build();
    }

    private OrderReceiptDTO getOrderReceipt(OrderDTO orderDTO, List<CartItemDTO> cartList,
        long totalPrice, long storeId, UserInfoDTO userInfo) {

        Store store = storeRepository.findById(storeId).get();
        StoreInfoDTO storeInfo = modelMapper.map(store, StoreInfoDTO.class);
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