package com.project.breakshop.models.DTO;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class UserInfoDTO {

    private final String id;

    private final String name;

    private final String phone;

    private final String address;

    @JsonCreator
    public UserInfoDTO(@JsonProperty(value = "id") String id,
        @JsonProperty(value = "name") String name,
        @JsonProperty(value = "phone") String phone,
        @JsonProperty(value = "address") String address) {
        this.id = id;
        this.name = name;
        this.phone = phone;
        this.address = address;
    }

}
