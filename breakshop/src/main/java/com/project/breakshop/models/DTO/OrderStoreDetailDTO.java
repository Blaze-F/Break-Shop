package com.project.breakshop.models.DTO;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@RequiredArgsConstructor
@Setter
public class OrderStoreDetailDTO {

    private  Long orderId;

    private  LocalDateTime orderCreatedAt;

    private  String orderStatus;

    private  Long totalPrice;

    private UserInfoDTO userInfo;

    private List<OrderDetailMenuDTO> menuList;

}
