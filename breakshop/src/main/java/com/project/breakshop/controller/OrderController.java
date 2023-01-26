package com.project.breakshop.controller;


import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/stores/{storeId}/orders")
@RequiredArgsConstructor
public class OrderController {

    private final OrderService orderService;

    @PostMapping
    @LoginCheck(userLevel = UserLevel.USER)
    public OrderReceiptDTO registerOrder(@CurrentUserId String userId, @PathVariable long storeId,
        PayType payType) {
        OrderReceiptDTO orderReceipt = orderService.registerOrder(userId, storeId, payType);
        return orderReceipt;
    }

    @GetMapping("/{orderId}")
    @LoginCheck(userLevel = UserLevel.USER)
    public OrderReceiptDTO loadOrder(@PathVariable long orderId) {
        return orderService.getOrderInfoByOrderId(orderId);
    }

    @GetMapping
    @LoginCheck(userLevel = UserLevel.OWNER)
    public List<OrderStoreDetailDTO> loadStoreOrder(@PathVariable long storeId) {
        return orderService.getStoreOrderInfoByStoreId(storeId);
    }
}
