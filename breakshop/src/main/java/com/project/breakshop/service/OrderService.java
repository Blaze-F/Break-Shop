package com.project.breakshop.service;
import com.project.breakshop.Redis.CartItemDAO;
import com.project.breakshop.models.DTO.*;
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

@Service
@RequiredArgsConstructor
public class OrderService {

    @Autowired
    private final OrderRepository orderRepository;
    @Autowired
    private final UserRepository userRepository;
    @Autowired
    private final OrderTransactionService orderTransactionService;
    @Autowired
    private final CartItemDAO cartItemDAO;
    @Autowired
    private final StoreRepository storeRepository;
    @Autowired
    private final ModelMapper modelMapper;

    @Transactional
    public OrderReceiptDTO registerOrder(String userId, long storeId, PayDTO.PayType payType) {

        UserInfoDTO user = userRepository.selectUserInfo(Long.parseLong(userId));
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


        orderMapper.completeOrder(totalPrice, orderDTO.getId(), OrderDTO.OrderStatus.COMPLETE_ORDER);
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

        StoreInfoDTO storeInfo = storeMapper.selectStoreInfo(storeId);
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
        OrderReceiptDTO orderReceipt = orderMapper.selectOrderReceipt(orderId);
        return orderReceipt;
    }

    public List<OrderStoreDetailDTO> getStoreOrderInfoByStoreId(long storeId) {
        List<OrderStoreDetailDTO> storeOrderDetailList = orderMapper
            .selectDetailStoreOrder(storeId);
        return storeOrderDetailList;
    }

}
