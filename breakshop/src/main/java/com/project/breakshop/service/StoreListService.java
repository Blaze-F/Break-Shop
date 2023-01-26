package com.project.breakshop.service;

import com.flab.makedel.dto.StoreCategoryDTO;
import com.flab.makedel.dto.StoreDTO;
import com.flab.makedel.mapper.StoreListMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class StoreListService {

    private final StoreListMapper storeListMapper;

    @Cacheable(value = "categories", key = "'store_category'")
    public List<StoreCategoryDTO> getAllStoreCategory() {
        return storeListMapper.selectCategoryList();
    }

    @Cacheable(value = "stores", key = "#categoryId")
    public List<StoreDTO> getStoreListByCategory(long categoryId) {
        return storeListMapper.selectStoreListByCategory(categoryId);
    }

    @Cacheable(value = "stores", key = "#address+#categoryId")
    public List<StoreDTO> getStoreListByCategoryAndAddress(long categoryId, String address) {
        return storeListMapper.selectStoreListByCategoryAndAddress(categoryId, address);
    }

}