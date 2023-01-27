package com.project.breakshop.service;

import com.flab.makedel.Exception.NotExistIdException;
import com.flab.makedel.dto.MenuDTO;
import com.flab.makedel.mapper.MenuMapper;
import com.project.breakshop.exception.NotExistIdException;
import com.project.breakshop.models.DTO.MenuDTO;
import com.project.breakshop.models.repository.MenuRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class MenuService {

    @Autowired
    private MenuRepository menuRepository;

    public void insertMenu(MenuDTO menu) {

    }

    public MenuDTO setStoreId(MenuDTO menu, long storeId) {
        MenuDTO newMenu = MenuDTO.builder()
            .name(menu.getName())
            .price(menu.getPrice())
            .photo(menu.getPhoto())
            .description(menu.getDescription())
            .menuGroupId(menu.getMenuGroupId())
            .storeId(storeId)
            .build();
        return newMenu;
    }

    public void deleteMenu(long menuId) {
        if (menuRepository.existsById(menuId)) {
            throw new NotExistIdException("존재하지 않는 메뉴 아이디 입니다 " + menuId);
        }
        menuRepository.deleteById(menuId);
    }

    public List<MenuDTO> loadStoreMenu(long storeId) {
        return menuMapper.selectStoreMenu(storeId);
    }

}
