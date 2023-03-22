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

    public  Long orderId;

    public  LocalDateTime orderCreatedAt;

    public  String orderStatus;

    public  Long totalPrice;

    public UserInfoDTO userInfo;

    public List<OrderDetailMenuDTO> menuList;

}
