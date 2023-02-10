package com.project.breakshop.models.DTO;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@RequiredArgsConstructor
public class OrderDetailDTO {

    private  Long orderId;

    private  String orderStatus;

    private Long totalPrice;

    private UserInfoDTO userInfo;

    private StoreInfoDTO storeInfo;

    private List<OrderDetailMenuDTO> menuList;
    
}
