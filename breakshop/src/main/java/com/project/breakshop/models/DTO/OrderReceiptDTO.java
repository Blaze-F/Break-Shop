package com.project.breakshop.models.DTO;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Builder
@Setter
public class OrderReceiptDTO {

    private Long orderId;

    private String orderStatus;

    private UserInfoDTO userInfo;

    private Long totalPrice;

    private StoreInfoDTO storeInfo;

    private List<CartItemDTO> cartList;

    @JsonCreator
    public OrderReceiptDTO(@JsonProperty(value = "orderId") Long orderId,
        @JsonProperty(value = "orderStatus") String orderStatus,
        @JsonProperty(value = "userInfo") UserInfoDTO userInfo,
        @JsonProperty(value = "totalPrice") Long totalPrice,
        @JsonProperty(value = "storeInfo") StoreInfoDTO storeInfo,
        @JsonProperty(value = "cartList") List<CartItemDTO> cartList) {
        this.orderId = orderId;
        this.orderStatus = orderStatus;
        this.userInfo = userInfo;
        this.totalPrice = totalPrice;
        this.storeInfo = storeInfo;
        this.cartList = cartList;
    }

    public OrderReceiptDTO(Long orderId, String orderStatus, Long totalPrice) {
        this.orderId = orderId;
        this.orderStatus = orderStatus;
        this.totalPrice = totalPrice;
    }

}
