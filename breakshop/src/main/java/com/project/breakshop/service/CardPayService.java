package com.project.breakshop.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CardPayService implements PayService {

    private final PayMapper payMapper;

    @Override
    public void pay(long price, long orderId) {
        PayDTO payDTO = PayDTO.builder()
            .payType(PayType.CARD)
            .price(price)
            .orderId(orderId)
            .status(PayStatus.COMPLETE_PAY)
            .build();
        payMapper.insertPay(payDTO);
    }
}

