package com.project.breakshop.models.DTO;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class OptionDTO {

    public Long id;

    public String name;

    public Long price;

    public  Long menuId;

}
