package com.project.breakshop.models.DTO;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.List;

@Getter
@RequiredArgsConstructor
public class OrderDetailMenuDTO {

    private final Long menuId;

    private final String menuName;

    private final Long menuPrice;

    private final Long menuCount;

    private List<OrderDetailOptionDTO> optionList;

}