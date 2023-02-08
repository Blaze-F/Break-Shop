package com.project.breakshop.service;

import com.project.breakshop.exception.NotExistIdException;
import com.project.breakshop.models.DTO.MenuDTO;
import com.project.breakshop.models.entity.Menu;
import com.project.breakshop.models.entity.Store;
import com.project.breakshop.models.repository.MenuRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@DataJpaTest
public class MenuServiceTest {

    @Mock
    MenuRepository menuRepository;

    @InjectMocks
    MenuService menuService;

    MenuDTO menuDTO;
    Store store;

    @BeforeEach
    public void init() {
        menuDTO = MenuDTO.builder()
            .name("뿌링클")
            .price(12300L)
            .photo("34.jpg")
            .description("맛있습니다")
            .storeId(3L)
            .build();
    }

    @Test
    @DisplayName("사장님이 가게 메뉴를 추가한다")
    public void insertMenuTest() {
        doNothing().when(menuRepository).save(any(Menu.class));

        menuService.insertMenu(menuDTO);

        verify(menuRepository).save(any(Menu.class));
    }

    @Test
    @DisplayName("사장님이 가게 메뉴를 삭제한다")
    public void deleteMenuTest() {
        doNothing().when(menuRepository).deleteById(anyLong());

        menuService.deleteMenu(12L);

        verify(menuRepository).deleteById(anyLong());
    }

    @Test
    @DisplayName("사장님이 가게 메뉴를 삭제할 때 잘못된 아이디로 요청하면 NotExistIdException을 던진다")
    public void deleteMenuTestThrowNotExistIdException() {
        when(menuRepository.existsById(anyLong())).thenReturn(false);

        assertThrows(NotExistIdException.class, () -> menuService.deleteMenu(12L));

        verify(menuRepository).existsById(anyLong());
    }

    @Test
    @DisplayName("모든 사용자가 가게 메뉴를 조회한다")
    public void loadStoreMenuTest() {
        when(menuRepository.findByStoreId(anyLong())).thenReturn(anyList());

        menuService.loadStoreMenu(12L);

        verify(menuRepository).findByStoreId(anyLong());
    }

    @Test
    @DisplayName("가게 메뉴를 조회할 때 없는 가게 아이디로 조회를 하면 빈 리스트를 리턴한다")
    public void loadStoreMenuTestReturnEmptyList() {
        when(menuRepository.findByStoreId(anyLong())).thenReturn(new ArrayList<Menu>());

        assertTrue(menuService.loadStoreMenu(12L).isEmpty());

        verify(menuRepository).findByStoreId(anyLong());
    }
}
