package com.project.breakshop.utils;

import com.project.breakshop.models.DTO.PayDTO;
import com.project.breakshop.service.CardPayService;
import com.project.breakshop.service.DepositPayService;
import com.project.breakshop.service.NaverPayService;
import com.project.breakshop.service.PayService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class PayServiceFactory {

    private final CardPayService cardPayService;
    private final NaverPayService naverPayService;
    private final DepositPayService depositPayService;

    public PayService getPayService(PayDTO.PayType payType) {

        PayService payService = null;

        switch (payType) {
            case CARD:
                payService = (PayService) cardPayService;
                break;
            case NAVER_PAY:
                payService = naverPayService;
                break;
            case DEPOSIT:
                payService = depositPayService;
                break;
            default:
                throw new IllegalArgumentException();
        }

        return payService;
    }

}
