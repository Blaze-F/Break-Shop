package com.project.breakshop.models.DTO;

import lombok.*;

import java.time.LocalDateTime;

@Getter
@AllArgsConstructor
@Builder
@Setter
public class OrderDTO {

    private final Long id;

    private final LocalDateTime createdAt;

    private final OrderStatus orderStatus;

    private final String address;

    private final String userId;

    private final Long storeId;

    private final Long totalPrice;

    public enum OrderStatus {
        BEFORE_ORDER, COMPLETE_ORDER, APPROVED_ORDER, DELIVERING, COMPLETE_DELIVERY
    }

}
