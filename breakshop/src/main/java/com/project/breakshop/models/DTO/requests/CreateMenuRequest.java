package com.project.breakshop.models.DTO.requests;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class CreateMenuRequest {
    public  Long id;

    public  String name;

    public  Long price;

    public  String photo;

    public  String description;

    public  Long storeId;
}
