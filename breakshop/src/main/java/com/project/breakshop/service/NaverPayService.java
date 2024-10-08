package com.project.breakshop.service;

import com.project.breakshop.models.DTO.PayDTO;
import com.project.breakshop.models.entity.Payments;
import com.project.breakshop.models.repository.PaymentsRepository;
import com.project.breakshop.service.interfaces.PayService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class NaverPayService implements PayService {

    private final PaymentsRepository paymentsRepository;
    private final ModelMapper modelMapper;

    @Override
    public void pay(long price, long orderId) {
        PayDTO payDTO = PayDTO.builder()
                .payType(PayDTO.PayType.NAVER_PAY)
                .price(price)
                .orderId(orderId)
                .status(PayDTO.PayStatus.COMPLETE_PAY)
                .build();

        Payments payments = modelMapper.map(payDTO, Payments.class);
        paymentsRepository.save(payments);
    }
}
