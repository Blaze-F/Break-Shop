package com.project.breakshop.models.DTO;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.project.breakshop.models.entity.Store;
import lombok.*;

@Getter
@Builder
@Setter
@NoArgsConstructor
public class StoreDTO {

    public  Long id;

    public  String name;

    public  String phone;

    public String address;

    public Long ownerId;

    public Store.OpenStatus openStatus;

    public String introduction;

    public Long categoryId;

    @JsonCreator
    public StoreDTO(@JsonProperty(value = "id") Long id,
        @JsonProperty(value = "name") String name,
        @JsonProperty(value = "phone") String phone,
        @JsonProperty(value = "address") String address,
        @JsonProperty(value = "ownerId") Long ownerId,
        @JsonProperty(value = "openStatus") Store.OpenStatus openStatus,
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
