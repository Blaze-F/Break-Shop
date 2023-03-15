package com.project.breakshop.controller;

import com.project.breakshop.annotation.CurrentUserId;
import com.project.breakshop.annotation.LoginCheck;
import com.project.breakshop.models.DTO.OrderReceiptDTO;
import com.project.breakshop.models.DTO.OrderStoreDetailDTO;
import com.project.breakshop.models.DTO.PayDTO.PayType;
import com.project.breakshop.service.OrderService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Tag(name = "/stores/{storeId}/orders", description = "주문 관련 API")
@RestController
@RequestMapping("/stores/{storeId}/orders")
@RequiredArgsConstructor
public class OrderController {

    private final OrderService orderService;

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    @LoginCheck(userLevel = LoginCheck.UserLevel.USER)
    @Operation(summary = "주문 등록", description = "가게에서 음식 주문을 등록합니다.")
    public OrderReceiptDTO registerOrder(@PathVariable long storeId, @CurrentUserId String userId,
                                         @RequestParam PayType payType) {
        return orderService.registerOrder(userId, storeId, payType);
    }

    @GetMapping(path = "/{orderId}", produces = MediaType.APPLICATION_JSON_VALUE)
    @LoginCheck(userLevel = LoginCheck.UserLevel.USER)
    @Operation(summary = "주문 조회", description = "주문 정보를 조회합니다.")
    public OrderReceiptDTO loadOrder(@PathVariable long orderId) {
        return orderService.getOrderInfoByOrderId(orderId);
    }

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    @LoginCheck(userLevel = LoginCheck.UserLevel.OWNER)
    @Operation(summary = "가게별 주문 조회", description = "가게별 주문 정보를 조회합니다.")
    public List<OrderStoreDetailDTO> loadStoreOrder(@PathVariable long storeId) {
        return orderService.getStoreOrderInfoByStoreId(storeId);
    }
}
