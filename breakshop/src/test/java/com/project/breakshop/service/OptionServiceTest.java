package com.project.breakshop.service;

import com.project.breakshop.models.DTO.OptionDTO;
import com.project.breakshop.models.entity.MenuOption;
import com.project.breakshop.models.repository.MenuOptionRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.anyList;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@DataJpaTest
public class OptionServiceTest {

    @Mock
    MenuOptionRepository menuOptionRepository;

    @InjectMocks
    OptionService optionService;

    List<OptionDTO> optionList;

    List<MenuOption> menuOptionList;

    @BeforeEach
    public void init() {
        optionList = new ArrayList<OptionDTO>();
        menuOptionList = new ArrayList<MenuOption>();
    }


    @Test
    @DisplayName("사장님이 음식 옵션을 추가한다")
    public void registerOptionListTest() {
        doNothing().when(menuOptionRepository).saveAll(anyList());

        optionService.registerOptionList(optionList);

        verify(menuOptionRepository).saveAll(anyList());
    }

    @Test
    @DisplayName("음식 메뉴에 대한 옵션리스트를 조회한다")
    public void loadOptionListTest() {
        when(menuOptionRepository.findByMenuId(anyLong())).thenReturn(menuOptionList);

        optionService.loadOptionList(12L);

        verify(menuOptionRepository).findByMenuId(anyLong());
    }

    @Test
    @DisplayName("잘못된 메뉴 아이디로 음식 메뉴에 대한 옵션리스트를 조회하면 빈 리스트를 리턴한다")
    public void loadOptionListTestReturnEmptyListBecauseWrongMenuId() {
        when(menuOptionRepository.findByMenuId(anyLong())).thenReturn(new ArrayList<>());

        assertTrue(optionService.loadOptionList(12L).isEmpty());

        verify(menuOptionRepository).findByMenuId(anyLong());
    }

    @Test
    @DisplayName("잘못된 가게 아이디로 음식 메뉴에 대한 옵션리스트를 조회하면 빈 리스트를 리턴한다")
    public void loadOptionListTestReturnEmptyListBecauseWrongStoreId() {
        when(menuOptionRepository.findByMenuId(anyLong())).thenReturn(new ArrayList<>());

        assertTrue(optionService.loadOptionList(12L).isEmpty());

        verify(menuOptionRepository).findByMenuId(anyLong());
    }

    @Test
    @DisplayName("가게 사장님이 메뉴에 대한 옵션을 삭제한다")
    public void deleteOptionListTest() {
        doNothing().when(menuOptionRepository).deleteAll(anyList());

        optionService.deleteOptionList(optionList);

        verify(menuOptionRepository).deleteAll(anyList());
    }
}
