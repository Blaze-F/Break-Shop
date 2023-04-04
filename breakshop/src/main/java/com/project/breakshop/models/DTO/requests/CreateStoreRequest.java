package com.project.breakshop.models.DTO.requests;

import lombok.*;

import javax.validation.constraints.NotNull;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CreateStoreRequest {

    public  String name;

    public  String phone;

    public String address;

    public Long ownerId;

    public String introduction;

    public String categoryName;
}
