package com.project.breakshop.service;


import com.project.breakshop.models.entity.StoreCategory;
import com.project.breakshop.models.repository.StoreCategoryRepository;
import com.project.breakshop.models.repository.StoreRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.anyList;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class StoreListServiceTest {

    @Mock
    StoreRepository storeRepository;

    @InjectMocks
    StoreListService storeListService;

    @Mock
    StoreCategoryRepository storeCategoryRepository;

    @Test
    @DisplayName("올바른 카테고리 아이디로 음식점 목록을 조회한다")
    public void getStoreListByCategoryTest() {
        when(storeRepository.findStoreByStoreCategoryId(anyLong())).thenReturn(anyList());

        storeListService.getStoreListByCategory(1L);

        verify(storeRepository).findStoreByStoreCategoryId(anyLong());
    }

    @Test
    @DisplayName("올바른 카테고리 아이디로 음식점 목록을 조회했는데 음식점 목록이 없다면 빈 리스트를 리턴한다")
    public void getStoreListByCategoryTestReturnEmptyList() {
        when(storeRepository.findStoreByStoreCategoryId(anyLong())).thenReturn(new ArrayList<>());

        assertEquals(storeListService.getStoreListByCategory(2L).isEmpty(), true);

        verify(storeRepository).findStoreByStoreCategoryId(anyLong());
    }

    @Test
    @DisplayName("잘못된 카테고리 아이디로 음식점 목록을 조회하면 RuntimeException을 던진다")
    public void getStoreListByCategoryTestFailBecauseWrongId() {
        doThrow(RuntimeException.class).when(storeRepository).findStoreByStoreCategoryId(anyLong());

        assertThrows(RuntimeException.class, () -> storeListService.getStoreListByCategory(100L));

        verify(storeRepository).findStoreByStoreCategoryId(anyLong());
    }

    @Test
    @DisplayName("음식 카테고리 전체 목록을 조회한다")
    public void getAllStoreCategoryTest() {
        when(storeCategoryRepository.findAll()).thenReturn(new ArrayList<StoreCategory>());

        storeListService.getAllStoreCategory();

        verify(storeCategoryRepository).findAll();
    }

}