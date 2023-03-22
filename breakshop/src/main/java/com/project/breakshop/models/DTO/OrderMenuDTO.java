package com.project.breakshop.models.DTO;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@AllArgsConstructor
@Builder
@Setter
public class OrderMenuDTO {

    public  Long orderId;

    public  Long menuId;

    public  Long count;

}
