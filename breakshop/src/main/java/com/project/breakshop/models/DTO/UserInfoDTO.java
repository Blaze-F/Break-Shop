package com.project.breakshop.models.DTO;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

@Getter
@Builder
@Setter
@NoArgsConstructor
public class UserInfoDTO {

    public String id;

    public String name;

    public String phone;

    public String address;

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
