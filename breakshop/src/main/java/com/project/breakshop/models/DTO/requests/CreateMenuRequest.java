package com.project.breakshop.models.DTO.requests;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CreateMenuRequest {
    public  Long id;

    public  String name;

    public  Long price;

    public  String photo;

    public  String description;

    public  Long storeId;
}
