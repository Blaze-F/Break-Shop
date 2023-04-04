package com.project.breakshop.models.DTO;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@RequiredArgsConstructor
public class OrderDetailMenuDTO {

    public  Long menuId;

    public  String menuName;

    public  Long menuPrice;

    public Long menuCount;

    public List<OrderDetailOptionDTO> optionList;

}