package com.project.breakshop.models.DTO;

import lombok.*;

import java.time.LocalDateTime;

@Getter
@AllArgsConstructor
@Builder
@Setter
@NoArgsConstructor
public class MenuDTO {

    public  Long id;

    public  String name;

    public  Long price;

    public  String photo;

    public  String description;

    public  Long storeId;

}
