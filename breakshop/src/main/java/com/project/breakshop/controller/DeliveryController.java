package com.project.breakshop.controller;


import com.project.breakshop.annotation.LoginCheck;
import com.project.breakshop.annotation.LoginCheck.UserLevel;
import com.project.breakshop.models.DTO.OrderReceiptDTO;
import com.project.breakshop.service.DeliveryService;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Tag(name = "/orders", description = "주문 요청 API")
@RestController
@RequestMapping(value = "/orders", params = "status")
@RequiredArgsConstructor
public class DeliveryController {

    private final DeliveryService deliveryService;


    @GetMapping(params = "orderId")
    @LoginCheck(userLevel = UserLevel.RIDER)
    public OrderReceiptDTO loadStandbyOrder(long orderId, String riderAddress) {
        return deliveryService.loadStandbyOrder(orderId, riderAddress);
    }

    @GetMapping
    @LoginCheck(userLevel = UserLevel.RIDER)
    public List<String> loadStandbyOrderList(String riderAddress) {
        return deliveryService.loadStandbyOrderList(riderAddress);
    }

}
