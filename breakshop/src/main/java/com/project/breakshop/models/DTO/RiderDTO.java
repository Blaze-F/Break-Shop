package com.project.breakshop.models.DTO;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotNull;

@Getter
@Builder
@Setter
public class RiderDTO {

    @NotNull
    private  String id;

    @NotNull
    private  String name;

    @NotNull
    private  String phone;

    @NotNull
    private  String address;

    @NotNull
    private  String updatedAt;

    @NotNull
    private  String fcmToken;

    @JsonCreator
    public RiderDTO(@JsonProperty(value = "id") String id,
        @JsonProperty(value = "name") String name,
        @JsonProperty(value = "phone") String phone,
        @JsonProperty(value = "address") String address,
        @JsonProperty(value = "updatedAt") String updatedAt,
        @JsonProperty(value = "fcmToken") String fcmToken) {
        this.id = id;
        this.name = name;
        this.phone = phone;
        this.address = address;
        this.updatedAt = updatedAt;
        this.fcmToken = fcmToken;
    }

}
