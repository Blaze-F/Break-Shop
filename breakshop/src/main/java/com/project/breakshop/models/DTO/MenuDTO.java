package com.project.breakshop.models.DTO;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@AllArgsConstructor
@Builder
@Setter
public class MenuDTO {

    private  Long id;

    private  String name;

    private  Long price;

    private  String photo;

    private  String description;

    private  Long storeId;

}
