package com.project.breakshop.models.DTO;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@RequiredArgsConstructor
public class OrderDetailOptionDTO {

    private final Long optionId;

    private final String optionName;

    private final Long optionPrice;

}
