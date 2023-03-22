package com.project.breakshop.models.DTO;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import javax.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class CartItemDTO {

    @NotNull
    public String name;

    @NotNull
    public Long price;

    @NotNull
    public Long menuId;

    @NotNull
    public Long storeId;

    @NotNull
    public Long count;

    @NotNull
    public List<CartOptionDTO> optionList;

    @JsonCreator
    public CartItemDTO(@JsonProperty(value = "name") String name,
        @JsonProperty(value = "price") Long price,
        @JsonProperty(value = "menuId") Long menuId,
        @JsonProperty(value = "storeId") Long storeId,
        @JsonProperty(value = "count") Long count,
        @JsonProperty(value = "optionList") List<CartOptionDTO> optionList
    ) {
        this.name = name;
        this.price = price;
        this.menuId = menuId;
        this.storeId = storeId;
        this.count = count;
        this.optionList = optionList;
    }

    public CartItemDTO(Long menuId, String name, Long price, Long count) {
        this.menuId = menuId;
        this.name = name;
        this.price = price;
        this.count = count;
    }


}
