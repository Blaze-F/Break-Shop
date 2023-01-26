package com.project.breakshop.models.DTO;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@RequiredArgsConstructor
public class OrderStoreDetailDTO {

    private final Long orderId;

    private final LocalDateTime orderCreatedAt;

    private final String orderStatus;

    private final Long totalPrice;

    private UserInfoDTO userInfo;

    private List<OrderDetailMenuDTO> menuList;

}
