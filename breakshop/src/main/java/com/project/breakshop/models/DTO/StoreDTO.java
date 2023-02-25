package com.project.breakshop.models.DTO;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

@Getter
@Builder
@Setter
@NoArgsConstructor
public class StoreDTO {

    private  Long id;

    private  String name;

    private  String phone;

    private String address;

    private Long ownerId;

    private String openStatus;

    private String introduction;

    private Long categoryId;

    @JsonCreator
    public StoreDTO(@JsonProperty(value = "id") Long id,
        @JsonProperty(value = "name") String name,
        @JsonProperty(value = "phone") String phone,
        @JsonProperty(value = "address") String address,
        @JsonProperty(value = "ownerId") Long ownerId,
        @JsonProperty(value = "openStatus") String openStatus,
        @JsonProperty(value = "introduction") String introduction,
        @JsonProperty(value = "categoryId") Long categoryId
    ) {
        this.id = id;
        this.name = name;
        this.phone = phone;
        this.address = address;
        this.ownerId = ownerId;
        this.openStatus = openStatus;
        this.introduction = introduction;
        this.categoryId = categoryId;
    }

}
