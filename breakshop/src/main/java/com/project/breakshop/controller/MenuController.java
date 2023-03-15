package com.project.breakshop.controller;

import com.project.breakshop.annotation.CurrentUserId;
import com.project.breakshop.annotation.LoginCheck;
import com.project.breakshop.models.DTO.MenuDTO;
import com.project.breakshop.service.MenuService;
import com.project.breakshop.service.StoreService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Tag(name = "/stores", description = "가게 정보 관련 API")
@RestController
@RequestMapping("/stores/{storeId}/menus")
@RequiredArgsConstructor
public class MenuController {

    private final MenuService menuService;
    private final StoreService storeService;

    @PostMapping
    @LoginCheck(userLevel = LoginCheck.UserLevel.OWNER)
    @Operation(summary = "메뉴 추가", description = "가게의 메뉴를 추가합니다.")
    public void insertMenu(@RequestBody MenuDTO menu, @PathVariable long storeId,
                           @CurrentUserId Long ownerId) {

        storeService.validateMyStore(storeId, ownerId);
        MenuDTO newMenu = menuService.setStoreId(menu, storeId);
        menuService.insertMenu(newMenu);

    }

    @DeleteMapping("/{menuId}")
    @LoginCheck(userLevel = LoginCheck.UserLevel.OWNER)
    @Operation(summary = "메뉴 삭제", description = "가게의 메뉴를 삭제합니다.")
    public void deleteMenu(@PathVariable Long menuId, @PathVariable long storeId,
                           @CurrentUserId Long ownerId) {

        storeService.validateMyStore(storeId, ownerId);
        menuService.deleteMenu(menuId);

    }

    @GetMapping
    @Operation(summary = "메뉴 목록 조회", description = "가게의 메뉴 목록을 조회합니다.")
    public ResponseEntity<List<MenuDTO>> loadStoreMenu(@PathVariable long storeId) {
        List<MenuDTO> menuList = menuService.loadStoreMenu(storeId);
        return ResponseEntity.ok().body(menuList);
    }

}