package com.project.breakshop.models.DTO;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.List;

@Getter
@RequiredArgsConstructor
public class OrderDetailDTO {

    private final Long orderId;

    private final String orderStatus;

    private final Long totalPrice;

    private UserInfoDTO userInfo;

    private StoreInfoDTO storeInfo;

    private List<OrderDetailMenuDTO> menuList;
    
}
