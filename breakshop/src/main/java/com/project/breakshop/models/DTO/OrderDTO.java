package com.project.breakshop.models.DTO;

import lombok.*;

import java.time.LocalDateTime;

@Getter
@AllArgsConstructor
@Builder
@Setter
@NoArgsConstructor
public class OrderDTO {

    public  Long id;

    public  LocalDateTime createdAt;

    public OrderStatus orderStatus;

    public String address;

    public String userId;

    public  Long storeId;

    public Long totalPrice;

    public enum OrderStatus {
        BEFORE_ORDER, COMPLETE_ORDER, APPROVED_ORDER, DELIVERING, COMPLETE_DELIVERY
    }



}
