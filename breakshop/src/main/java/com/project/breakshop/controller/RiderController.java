package com.project.breakshop.controller;


import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/riders")
@RequiredArgsConstructor
public class RiderController {

    private final RiderService riderService;

    @PostMapping("/{riderId}/standby")
    @LoginCheck(userLevel = UserLevel.RIDER)
    public void registerStandbyRider(RiderDTO rider) {
        riderService.registerStandbyRiderWhenStartWork(rider);
    }

    @DeleteMapping("/{riderId}/standby")
    @LoginCheck(userLevel = UserLevel.RIDER)
    public void deleteStandbyRider(RiderDTO rider) {
        riderService.deleteStandbyRiderWhenStopWork(rider);
    }

    @PatchMapping("/{riderId}/orders/{orderId}/accept")
    @LoginCheck(userLevel = UserLevel.RIDER)
    public void acceptStandbyOrder(@PathVariable long orderId,
        RiderDTO rider) {
        riderService.acceptStandbyOrder(orderId, rider);
    }

    @PatchMapping("/{riderId}/orders/{orderId}/finish")
    @LoginCheck(userLevel = UserLevel.RIDER)
    public void finishDeliveringOrder(@PathVariable long orderId, RiderDTO rider) {
        riderService.finishDeliveringOrder(orderId, rider);
    }
    
}
