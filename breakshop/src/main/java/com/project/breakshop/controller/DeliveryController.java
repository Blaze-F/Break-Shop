package com.project.breakshop.controller;


import com.project.breakshop.annotation.LoginCheck;
import com.project.breakshop.annotation.LoginCheck.UserLevel;
import com.project.breakshop.models.DTO.OrderReceiptDTO;
import com.project.breakshop.service.DeliveryService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Tag(name = "/orders", description = "주문 요청 API")
@RestController
@RequestMapping(value = "/orders", params = "status")
@RequiredArgsConstructor
public class DeliveryController {

    private final DeliveryService deliveryService;


    @Operation(summary = "특정 주문 조회 API")
    @GetMapping(params = "orderId")
    @LoginCheck(userLevel = UserLevel.ROLE_RIDER)
    public OrderReceiptDTO loadStandbyOrder(@RequestParam long orderId,
                                            @RequestParam String riderAddress) {
        return deliveryService.loadStandbyOrder(orderId, riderAddress);
    }

    @Operation(summary = "대기 중인 주문 리스트 조회 API")
    @GetMapping
    @LoginCheck(userLevel = UserLevel.ROLE_RIDER)
    public List<String> loadStandbyOrderList(@RequestParam String riderAddress) {
        return deliveryService.loadStandbyOrderList(riderAddress);
    }

}
