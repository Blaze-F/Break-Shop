package com.project.breakshop.controller;

import com.project.breakshop.annotation.CurrentUserId;
import com.project.breakshop.annotation.LoginCheck;
import com.project.breakshop.models.DTO.OptionDTO;
import com.project.breakshop.service.OptionService;
import com.project.breakshop.service.StoreService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeType;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.security.SecurityScheme;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Tag(name = "/stores/{storeId}/menus/{menuId}/options", description = "메뉴 옵션 관련 API")
@RestController
@RequestMapping("/stores/{storeId}/menus/{menuId}/options")
@RequiredArgsConstructor
@SecurityScheme(name = "bearerAuth", type = SecuritySchemeType.HTTP, scheme = "bearer")
public class OptionController {

    private final OptionService optionService;
    private final StoreService storeService;

    @PostMapping
    @LoginCheck(userLevel = LoginCheck.UserLevel.ROLE_OWNER)
    @Operation(summary = "옵션 리스트 등록", description = "메뉴 옵션 리스트를 등록합니다.")
    @SecurityRequirement(name = "bearerAuth")
    public void registerOptionList(
            @Parameter(description = "옵션 리스트", required = true) @RequestBody List<OptionDTO> optionList,
            @Parameter(description = "가게 ID", required = true) @PathVariable long storeId,
            @Parameter(description = "소유자 ID", required = true) @CurrentUserId String ownerEmail) {

        storeService.validateMyStore(storeId, ownerEmail);
        optionService.registerOptionList(optionList);
    }

    @GetMapping
    @Operation(summary = "옵션 리스트 조회", description = "메뉴 옵션 리스트를 조회합니다.")
    public ResponseEntity<List<OptionDTO>> loadOptionList(
            @Parameter(description = "메뉴 ID", required = true) @PathVariable long menuId) {
        List<OptionDTO> optionList = optionService.loadOptionList(menuId);
        return ResponseEntity.ok().body(optionList);
    }

    @DeleteMapping
    @LoginCheck(userLevel = LoginCheck.UserLevel.ROLE_OWNER)
    @Operation(summary = "옵션 리스트 삭제", description = "메뉴 옵션 리스트를 삭제합니다.")
    @SecurityRequirement(name = "bearerAuth")
    public void deleteOptionList(
            @Parameter(description = "옵션 리스트", required = true) @RequestBody List<OptionDTO> optionList,
            @Parameter(description = "가게 ID", required = true) @PathVariable long storeId,
            @Parameter(description = "소유자 ID", required = true) @CurrentUserId String ownerEmail) {

        storeService.validateMyStore(storeId, ownerEmail);
        optionService.deleteOptionList(optionList);
    }
}