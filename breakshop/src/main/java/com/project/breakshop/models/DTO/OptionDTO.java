package com.project.breakshop.models.DTO;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class OptionDTO {

    private Long id;

    private String name;

    private Long price;

    private  Long menuId;

}
