package com.project.breakshop.service;


import com.project.breakshop.models.DTO.OptionDTO;
import com.project.breakshop.models.entity.MenuOption;
import com.project.breakshop.models.repository.MenuOptionRepository;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class OptionService {

    ModelMapper modelMapper = new ModelMapper();
    @Autowired
    private final MenuOptionRepository menuOptionRepository;

    public void registerOptionList(List<OptionDTO> optionList) {
        Set<MenuOption> menuOptionSet = new HashSet<>();
        menuOptionSet = optionList.stream().map(p -> modelMapper.map(p, MenuOption.class)).collect(Collectors.toSet());
        menuOptionRepository.saveAll(menuOptionSet);
    }

    public List<OptionDTO> loadOptionList(long menuId) {

        List<MenuOption> entityList =  menuOptionRepository.findByMenuId(menuId);

        return entityList.stream().map(p -> modelMapper.map(p, OptionDTO.class)).collect(Collectors.toList());
    }

    public void deleteOptionList(List<OptionDTO> optionList) {

        Set<MenuOption>  menuOptionSet = optionList.stream().map(e -> modelMapper.map(e,MenuOption.class)).collect(Collectors.toSet());

        menuOptionRepository.deleteAll(menuOptionSet);
    }
}
