package com.project.breakshop.service;

import com.project.breakshop.exception.NotExistIdException;
import com.project.breakshop.models.DTO.MenuDTO;
import com.project.breakshop.models.entity.Menu;
import com.project.breakshop.models.repository.MenuRepository;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class MenuService {

    @Autowired
    private final MenuRepository menuRepository;
    private final ModelMapper modelMapper;

    public void insertMenu(MenuDTO menu) {
        Menu menuEntity = modelMapper.map(menu, Menu.class);
        menuRepository.save(menuEntity);
    }

    public MenuDTO setStoreId(MenuDTO menu, long storeId) {
        return MenuDTO.builder()
            .name(menu.getName())
            .price(menu.getPrice())
            .photo(menu.getPhoto())
            .description(menu.getDescription())
            .storeId(storeId)
            .build();
    }

    public void deleteMenu(long menuId) {
        if (!menuRepository.existsById(menuId)) {
            throw new NotExistIdException("존재하지 않는 메뉴 아이디 입니다 " + menuId);
        }
        menuRepository.deleteById(menuId);
    }

    public List<MenuDTO> loadStoreMenu(long storeId) {
        List<Menu> menuEntityList =  menuRepository.findByStoreId(storeId);
        //ModelMapper에 List를 바인딩
        return menuEntityList.stream().map(p -> modelMapper.map(p, MenuDTO.class)).collect(Collectors.toList());
    }

}
