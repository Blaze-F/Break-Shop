package com.project.breakshop.models.DTO;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@RequiredArgsConstructor
public class OrderDetailDTO {

    public  Long orderId;

    public  String orderStatus;

    public Long totalPrice;

    public UserInfoDTO userInfo;

    public StoreInfoDTO storeInfo;

    public List<OrderDetailMenuDTO> menuList;
    
}
