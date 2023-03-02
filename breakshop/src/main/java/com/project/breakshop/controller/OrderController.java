package com.project.breakshop.controller;


import com.project.breakshop.annotation.CurrentUserId;
import com.project.breakshop.annotation.LoginCheck;
import com.project.breakshop.annotation.LoginCheck.UserLevel;
import com.project.breakshop.models.DTO.OrderReceiptDTO;
import com.project.breakshop.models.DTO.OrderStoreDetailDTO;
import com.project.breakshop.models.DTO.PayDTO.PayType;
import com.project.breakshop.service.OrderService;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
@Tag(name = "/stores/{storeId}/orders", description = "주문 관련 API")
@RestController
@RequestMapping("/stores/{storeId}/orders")
@RequiredArgsConstructor
public class OrderController {

    @Autowired
    private final OrderService orderService;

    @PostMapping
    @LoginCheck(userLevel = UserLevel.USER)
    public OrderReceiptDTO registerOrder(@CurrentUserId String userId, @PathVariable long storeId,
                                         PayType payType) {
        return orderService.registerOrder(userId, storeId, payType);
    }

    @GetMapping("/{orderId}")
    @LoginCheck(userLevel = UserLevel.USER)
    public OrderReceiptDTO loadOrder(@PathVariable long orderId) {
        return orderService.getOrderInfoByOrderId(orderId);
    }

    @GetMapping
    @LoginCheck(userLevel = LoginCheck.UserLevel.OWNER)
    public List<OrderStoreDetailDTO> loadStoreOrder(@PathVariable long storeId) {
        return orderService.getStoreOrderInfoByStoreId(storeId);
    }
}
