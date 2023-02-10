package com.project.breakshop.models.DTO;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@AllArgsConstructor
@Builder
@Setter
public class OrderMenuDTO {

    private final Long orderId;

    private final Long menuId;

    private final Long count;

}
