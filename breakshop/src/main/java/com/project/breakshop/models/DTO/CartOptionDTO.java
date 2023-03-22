package com.project.breakshop.models.DTO;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
public class CartOptionDTO {

    public Long optionId;

    public String name;

    public Long price;

    @JsonCreator
    public CartOptionDTO(
        @JsonProperty(value = "optionId") Long optionId,
        @JsonProperty(value = "name") String name,
        @JsonProperty(value = "price") Long price) {
        this.optionId = optionId;
        this.name = name;
        this.price = price;
    }

}
