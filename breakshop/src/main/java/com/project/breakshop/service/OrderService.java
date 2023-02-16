package com.project.breakshop.service;
import com.project.breakshop.Redis.CartItemDAO;
import com.project.breakshop.models.DTO.*;
import com.project.breakshop.models.entity.Order;
import com.project.breakshop.models.entity.Store;
import com.project.breakshop.models.entity.User;
import com.project.breakshop.models.repository.OrderRepository;
import com.project.breakshop.models.repository.StoreRepository;
import com.project.breakshop.models.repository.UserRepository;
import lombok.RequiredArgsConstructor;
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
@RequiredArgsConstructor
public class OrderService {

    private final OrderRepository orderRepository;

    private final UserRepository userRepository;

    private final OrderTransactionService orderTransactionService;

    private final CartItemDAO cartItemDAO;

    private final StoreRepository storeRepository;

    private ModelMapper modelMapper;

    @Transactional
    public OrderReceiptDTO registerOrder(String userId, long storeId, PayDTO.PayType payType) {

        User userEntity = userRepository.findById(Long.parseLong(userId)).get();
        UserInfoDTO user = modelMapper.map(userEntity, UserInfoDTO.class);
        OrderDTO orderDTO = getOrderDTO(user, storeId);
        List<CartItemDTO> cartList;
        List<OrderMenuDTO> orderMenuList = new ArrayList<>();
        List<OrderMenuOptionDTO> orderMenuOptionList = new ArrayList<>();
        OrderReceiptDTO orderReceipt;

        cartList = cartItemDAO.getCartAndDelete(userId);

        restoreCartListOnOrderRollback(userId, cartList);

        long totalPrice = orderTransactionService
            .order(orderDTO, cartList, orderMenuList, orderMenuOptionList);
        orderTransactionService.pay(payType, totalPrice, orderDTO.getId());

        orderDTO.setOrderStatus(OrderDTO.OrderStatus.COMPLETE_ORDER);
        orderRepository.save(modelMapper.map(orderDTO, Order.class));
        orderReceipt = getOrderReceipt(orderDTO, cartList, totalPrice, storeId,
            user);

        return orderReceipt;
    }

    private void restoreCartListOnOrderRollback(String userId, List<CartItemDTO> cartList) {
        TransactionSynchronizationManager.registerSynchronization(
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
