package com.project.breakshop.controller;


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
    @LoginCheck(userLevel = UserLevel.OWNER)
    public void registerOptionList(@RequestBody List<OptionDTO> optionList,
        @PathVariable long storeId, @CurrentUserId String ownerId) {

        storeService.validateMyStore(storeId, ownerId);
        optionService.registerOptionList(optionList);

    }

    @GetMapping
    public ResponseEntity<List<OptionDTO>> loadOptionList(@PathVariable long menuId) {
        List<OptionDTO> optionList = optionService.loadOptionList(menuId);
        return ResponseEntity.ok().body(optionList);
    }

    @DeleteMapping
    @LoginCheck(userLevel = UserLevel.OWNER)
    public void deleteOptionList(@RequestBody List<OptionDTO> optionList,
        @PathVariable long storeId, @CurrentUserId String ownerId) {

        storeService.validateMyStore(storeId, ownerId);
        optionService.deleteOptionList(optionList);

    }

}
