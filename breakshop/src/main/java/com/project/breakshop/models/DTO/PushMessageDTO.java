package com.project.breakshop.models.DTO;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Builder
@Setter
public class PushMessageDTO {

    private  String title;

    private  String content;

    private  OrderReceiptDTO orderReceipt;

    private  String createdAt;

    public static final String RIDER_MESSAGE_TITLE = "배차 요청";
    public static final String RIDER_MESSAGE_CONTENT = "근처 가게에서 주문이 승인된 후 배차 요청이 도착했습니다. 승인하시겠습니까?";

}
