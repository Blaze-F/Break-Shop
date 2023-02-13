package com.project.breakshop.controller;


import com.project.breakshop.annotation.CurrentUserId;
import com.project.breakshop.annotation.LoginCheck;
import com.project.breakshop.models.DTO.OptionDTO;
import com.project.breakshop.service.OptionService;
import com.project.breakshop.service.StoreService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/stores/{storeId}/menus/{menuId}/options")
@RequiredArgsConstructor
public class OptionController {

    private final OptionService optionService;
    private final StoreService storeService;

    @PostMapping
    @LoginCheck(userLevel = LoginCheck.UserLevel.OWNER)
    public void registerOptionList(@RequestBody List<OptionDTO> optionList,
        @PathVariable long storeId, @CurrentUserId Long ownerId) {

        storeService.validateMyStore(storeId, ownerId);
        optionService.registerOptionList(optionList);

    }

    @GetMapping
    public ResponseEntity<List<OptionDTO>> loadOptionList(@PathVariable long menuId) {
        List<OptionDTO> optionList = optionService.loadOptionList(menuId);
        return ResponseEntity.ok().body(optionList);
    }

    @DeleteMapping
    @LoginCheck(userLevel = LoginCheck.UserLevel.OWNER)
    public void deleteOptionList(@RequestBody List<OptionDTO> optionList,
        @PathVariable long storeId, @CurrentUserId Long ownerId) {

        storeService.validateMyStore(storeId, ownerId);
        optionService.deleteOptionList(optionList);

    }

}
