package com.project.breakshop.controller;


import com.project.breakshop.annotation.LoginCheck;
import com.project.breakshop.annotation.LoginCheck.UserLevel;
import com.project.breakshop.models.DTO.RiderDTO;
import com.project.breakshop.service.RiderService;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
@Tag(name = "/riders", description = "라이더 배차 관련 API")
@RestController
@RequestMapping("/riders")
@RequiredArgsConstructor
public class RiderController {

    @Autowired
    private final RiderService riderService;

    @PostMapping("/{riderId}/standby")
    @LoginCheck(userLevel = UserLevel.ROLE_RIDER)
    public void registerStandbyRider(RiderDTO rider) {
        riderService.registerStandbyRiderWhenStartWork(rider);
    }

    @DeleteMapping("/{riderId}/standby")
    @LoginCheck(userLevel = UserLevel.ROLE_RIDER)
    public void deleteStandbyRider(RiderDTO rider) {
        riderService.deleteStandbyRiderWhenStopWork(rider);
    }

    @PatchMapping("/{riderId}/orders/{orderId}/accept")
    @LoginCheck(userLevel = UserLevel.ROLE_RIDER)
    public void acceptStandbyOrder(@PathVariable long orderId,
                                   RiderDTO rider) {
        riderService.acceptStandbyOrder(orderId, rider);
    }

    @PatchMapping("/{riderId}/orders/{orderId}/finish")
    @LoginCheck(userLevel = UserLevel.ROLE_RIDER)
    public void finishDeliveringOrder(@PathVariable long orderId, RiderDTO rider) {
        riderService.finishDeliveringOrder(orderId, rider);
    }

}
