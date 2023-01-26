package com.project.breakshop.models.DTO;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class StoreInfoDTO {

    private final Long storeId;

    private final String name;

    private final String phone;

    private final String address;

    @JsonCreator
    public StoreInfoDTO(@JsonProperty(value = "storeId") Long storeId,
        @JsonProperty(value = "name") String name,
        @JsonProperty(value = "phone") String phone,
        @JsonProperty(value = "address") String address) {
        this.storeId = storeId;
        this.name = name;
        this.phone = phone;
        this.address = address;
    }

}