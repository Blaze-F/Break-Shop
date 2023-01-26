package com.project.breakshop.models.DTO;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class OptionDTO {

    private final Long id;

    private final String name;

    private final Long price;

    private final Long menuId;

}
