package com.project.breakshop.models.DTO;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@AllArgsConstructor
@Builder
@Setter
public class OrderMenuOptionDTO {

    public  Long menuId;

    public  Long orderId;

    public  Long optionId;

}
