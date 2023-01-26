package com.project.breakshop.service;

import com.flab.makedel.dto.OptionDTO;
import com.flab.makedel.mapper.OptionMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class OptionService {

    private final OptionMapper optionMapper;

    public void registerOptionList(List<OptionDTO> optionList) {
        optionMapper.insertOptionList(optionList);
    }

    public List<OptionDTO> loadOptionList(long menuId) {
        return optionMapper.selectOptionList(menuId);
    }

    public void deleteOptionList(List<OptionDTO> optionList) {
        optionMapper.deleteOptionList(optionList);
    }

}
