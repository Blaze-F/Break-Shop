package com.project.breakshop.models.DTO;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class OrderDetailOptionDTO {

    private final Long optionId;

    private final String optionName;

    private final Long optionPrice;

}
