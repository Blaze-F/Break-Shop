package com.project.breakshop.service;


import com.project.breakshop.models.DTO.PayDTO;
import com.project.breakshop.models.entity.Payments;
import com.project.breakshop.models.repository.PaymentsRepository;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class DepositPayService implements PayService {

    ModelMapper modelMapper = new ModelMapper();

    @Autowired
    PaymentsRepository paymentsRepository;

    @Override
    public void pay(long price, long orderId) {
        PayDTO payDTO = PayDTO.builder()
            .payType(PayDTO.PayType.DEPOSIT)
            .price(price)
            .orderId(orderId)
            .status(PayDTO.PayStatus.BEFORE_DEPOSIT)
            .build();

        Payments payments = modelMapper.map(payDTO, Payments.class);

        paymentsRepository.save(payments);

    }
}
