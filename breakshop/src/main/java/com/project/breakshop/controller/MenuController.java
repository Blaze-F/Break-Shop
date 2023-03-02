package com.project.breakshop.controller;


import com.project.breakshop.annotation.CurrentUserId;
import com.project.breakshop.annotation.LoginCheck;
import com.project.breakshop.models.DTO.MenuDTO;
import com.project.breakshop.service.MenuService;
import com.project.breakshop.service.StoreService;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
@Tag(name = "/stores", description = "가게 정보 관련 API")
@RestController
@RequestMapping("/stores/{storeId}/menus")
@RequiredArgsConstructor
public class MenuController {

    private final MenuService menuService;
    private final StoreService storeService;

    @PostMapping
    @LoginCheck(userLevel = LoginCheck.UserLevel.OWNER)
    public void insertMenu(MenuDTO menu, @PathVariable long storeId,
        @CurrentUserId Long ownerId) {

        storeService.validateMyStore(storeId, ownerId);
        MenuDTO newMenu = menuService.setStoreId(menu, storeId);
        menuService.insertMenu(newMenu);

    }

    @DeleteMapping("/{menuId}")
    @LoginCheck(userLevel = LoginCheck.UserLevel.OWNER)
    public void deleteMenu(@PathVariable Long menuId, @PathVariable long storeId,
        @CurrentUserId Long ownerId) {

        storeService.validateMyStore(storeId, ownerId);
        menuService.deleteMenu(menuId);

    }

    @GetMapping
    public ResponseEntity<List<MenuDTO>> loadStoreMenu(@PathVariable long storeId) {
        List<MenuDTO> menuList = menuService.loadStoreMenu(storeId);
        return ResponseEntity.ok().body(menuList);
    }

}
