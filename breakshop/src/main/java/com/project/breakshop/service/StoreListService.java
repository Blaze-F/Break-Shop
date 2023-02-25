package com.project.breakshop.service;

import com.project.breakshop.models.DTO.StoreCategoryDTO;
import com.project.breakshop.models.DTO.StoreDTO;
import com.project.breakshop.models.entity.Store;
import com.project.breakshop.models.entity.StoreCategory;
import com.project.breakshop.models.repository.StoreCategoryRepository;
import com.project.breakshop.models.repository.StoreRepository;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class StoreListService {

    private final StoreRepository storeRepository;
    ModelMapper modelMapper = new ModelMapper();
    private final StoreCategoryRepository storeCategoryRepository;


    @Cacheable(value = "categories", key = "'store_category'")
    public List<StoreCategoryDTO> getAllStoreCategory() {

        List<StoreCategory> storeCategories = storeCategoryRepository.findAll();
        return storeCategories.stream().map(e -> modelMapper.map(e, StoreCategoryDTO.class)).collect(Collectors.toList());
    }

    @Cacheable(value = "stores", key = "#categoryId")
    public List<StoreDTO> getStoreListByCategory(long categoryId) {
        List<Store> stores =  storeRepository.findStoreByStoreCategoryId(categoryId);

        return stores.stream().map(e-> modelMapper.map(e,StoreDTO.class)).collect(Collectors.toList());
    }

    @Cacheable(value = "stores", key = "#address+#categoryId")
    public List<StoreDTO> getStoreListByCategoryAndAddress(long categoryId, String address) {
        List<Store> stores = storeRepository.findStoreByStoreCategoryIdAndAddress(categoryId, address);
        return stores.stream().map(e -> modelMapper.map(e, StoreDTO.class)).collect(Collectors.toList());
    }

}
