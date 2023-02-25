package com.project.breakshop.models.DTO;

import lombok.*;

import java.time.LocalDateTime;

@Getter
@AllArgsConstructor
@Builder
@Setter
@NoArgsConstructor
public class OrderDTO {

    private  Long id;

    private  LocalDateTime createdAt;

    private OrderStatus orderStatus;

    private String address;

    private String userId;

    private  Long storeId;

    private Long totalPrice;

    public enum OrderStatus {
        BEFORE_ORDER, COMPLETE_ORDER, APPROVED_ORDER, DELIVERING, COMPLETE_DELIVERY
    }



}
