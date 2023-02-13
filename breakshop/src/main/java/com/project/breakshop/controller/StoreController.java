package com.project.breakshop.controller;


import com.project.breakshop.annotation.CurrentUserId;
import com.project.breakshop.annotation.LoginCheck;
import com.project.breakshop.annotation.LoginCheck.UserLevel;
import com.project.breakshop.models.DTO.StoreDTO;
import com.project.breakshop.service.StoreService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static com.project.breakshop.utils.ResponseEntityConstants.RESPONSE_OK;


@RestController
@RequestMapping("/stores")
@RequiredArgsConstructor
public class StoreController {

    private final StoreService storeService;

    @PostMapping
    @LoginCheck(userLevel = UserLevel.OWNER)
    public ResponseEntity<Void> insertStore(StoreDTO store, @CurrentUserId Long ownerId) {

        storeService.insertStore(store, ownerId);
        return RESPONSE_OK;

    }

    @GetMapping
    @LoginCheck(userLevel = UserLevel.OWNER)
    public ResponseEntity<List<StoreDTO>> getMyAllStore(@CurrentUserId String ownerEmail) {

        List<StoreDTO> stores = storeService.findMyAllStore(ownerEmail);
        return ResponseEntity.ok().body(stores);

    }

    @GetMapping("/{storeId}")
    @LoginCheck(userLevel = UserLevel.OWNER)
    public ResponseEntity<StoreDTO> getMyStore(@PathVariable long storeId,
        @CurrentUserId Long ownerId) {

        storeService.validateMyStore(storeId, ownerId);
        StoreDTO store = storeService.getMyStore(storeId, ownerId);
        return ResponseEntity.ok().body(store);

    }

    @PatchMapping("/{storeId}/closed")
    @LoginCheck(userLevel = UserLevel.OWNER)
    public ResponseEntity<Void> closeMyStore(@PathVariable long storeId,
        @CurrentUserId Long ownerId) {

        storeService.validateMyStore(storeId, ownerId);
        storeService.closeMyStore(storeId);
        return RESPONSE_OK;

    }

    @PatchMapping("/{storeId}/opened")
    @LoginCheck(userLevel = UserLevel.OWNER)
    public ResponseEntity<Void> openMyStore(@PathVariable long storeId,
        @CurrentUserId Long ownerId) {

        storeService.validateMyStore(storeId, ownerId);
        storeService.openMyStore(storeId);
        return RESPONSE_OK;

    }

    @PostMapping("/{storeId}/orders/{orderId}/approve")
    @LoginCheck(userLevel = UserLevel.OWNER)
    public void approveOrder(@PathVariable long orderId, @PathVariable long storeId,
        @CurrentUserId Long ownerId) {
        storeService.validateMyStore(storeId, ownerId);
        storeService.approveOrder(orderId);
    }

}
