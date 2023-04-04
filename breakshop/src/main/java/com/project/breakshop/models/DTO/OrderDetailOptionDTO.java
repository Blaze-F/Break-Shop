package com.project.breakshop.models.DTO;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@RequiredArgsConstructor
public class OrderDetailOptionDTO {

    public  Long optionId;

    public  String optionName;

    public  Long optionPrice;

}
