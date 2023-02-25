package com.project.breakshop.models.DTO;

import lombok.*;

import java.time.LocalDateTime;

@Getter
@AllArgsConstructor
@Builder
@Setter
@NoArgsConstructor
public class MenuDTO {

    private  Long id;

    private  String name;

    private  Long price;

    private  String photo;

    private  String description;

    private  Long storeId;

}
