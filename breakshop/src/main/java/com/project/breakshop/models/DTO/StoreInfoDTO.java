package com.project.breakshop.models.DTO;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Builder
@Setter
public class StoreInfoDTO {

    public Long storeId;

    private String name;

    private String phone;

    private String address;

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