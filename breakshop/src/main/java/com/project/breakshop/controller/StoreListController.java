package com.project.breakshop.controller;


import com.project.breakshop.models.DTO.StoreCategoryDTO;
import com.project.breakshop.models.DTO.StoreDTO;
import com.project.breakshop.service.StoreListService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/stores")
@RequiredArgsConstructor
public class StoreListController {

    private final StoreListService storeListService;

    @GetMapping("/categories")
    public ResponseEntity<List<StoreCategoryDTO>> loadStoreCategory() {
        List<StoreCategoryDTO> categoryList = storeListService.getAllStoreCategory();
        return ResponseEntity.ok().body(categoryList);
    }

    @GetMapping(params = "categoryId")
    public ResponseEntity<List<StoreDTO>> loadStoreListByCategory(long categoryId) {
        List<StoreDTO> storeList = storeListService.getStoreListByCategory(categoryId);
        return ResponseEntity.ok().body(storeList);
    }

    @GetMapping(params = {"categoryId", "address"})
    public ResponseEntity<List<StoreDTO>> loadStoreListByCategoryAndAddress(long categoryId,
        String address) {
        List<StoreDTO> storeList = storeListService
            .getStoreListByCategoryAndAddress(categoryId, address);
        return ResponseEntity.ok().body(storeList);
    }
    
}
