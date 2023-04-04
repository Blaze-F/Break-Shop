package com.project.breakshop.controller;


import com.project.breakshop.annotation.CurrentUserId;
import com.project.breakshop.annotation.LoginCheck;
import com.project.breakshop.annotation.LoginCheck.UserLevel;
import com.project.breakshop.models.DTO.StoreDTO;
import com.project.breakshop.models.DTO.requests.CreateStoreRequest;
import com.project.breakshop.service.StoreService;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static com.project.breakshop.utils.ResponseEntityConstants.RESPONSE_OK;

@Tag(name = "/stores", description = "상점관리 관련 API")
@RestController
@RequestMapping("/stores")
@RequiredArgsConstructor
public class StoreController {

    private final StoreService storeService;

    @PostMapping
    @LoginCheck(userLevel = UserLevel.ROLE_OWNER)
    public ResponseEntity<Void> insertStore(CreateStoreRequest store, @CurrentUserId String ownerEmail) {
        storeService.insertStore(store, ownerEmail);
        return RESPONSE_OK;

    }

    @GetMapping
    @LoginCheck(userLevel = UserLevel.ROLE_OWNER)
    public ResponseEntity<List<StoreDTO>> getMyAllStore(@CurrentUserId String ownerEmail) {

        List<StoreDTO> stores = storeService.findMyAllStore(ownerEmail);
        return ResponseEntity.ok().body(stores);

    }

    @GetMapping("/{storeId}")
    @LoginCheck(userLevel = UserLevel.ROLE_OWNER)
    public ResponseEntity<StoreDTO> getMyStore(@PathVariable long storeId,
        @CurrentUserId String ownerEmail) {

        storeService.validateMyStore(storeId, ownerEmail);
        StoreDTO store = storeService.getMyStore(storeId, ownerEmail);
        return ResponseEntity.ok().body(store);

    }

    @PatchMapping("/{storeId}/closed")
    @LoginCheck(userLevel = UserLevel.ROLE_OWNER)
    public ResponseEntity<Void> closeMyStore(@PathVariable long storeId,
        @CurrentUserId String ownerEmail) {

        storeService.validateMyStore(storeId, ownerEmail);
        storeService.closeMyStore(storeId);
        return RESPONSE_OK;

    }

    @PatchMapping("/{storeId}/opened")
    @LoginCheck(userLevel = UserLevel.ROLE_OWNER)
    public ResponseEntity<Void> openMyStore(@PathVariable long storeId,
        @CurrentUserId String ownerEmail) {

        storeService.validateMyStore(storeId, ownerEmail);
        storeService.openMyStore(storeId);
        return RESPONSE_OK;

    }

    @PostMapping("/{storeId}/orders/{orderId}/approve")
    @LoginCheck(userLevel = UserLevel.ROLE_OWNER)
    public void approveOrder(@PathVariable long orderId, @PathVariable long storeId,
        @CurrentUserId String ownerEmail) {
        storeService.validateMyStore(storeId, ownerEmail);
        storeService.approveOrder(orderId);
    }

}
