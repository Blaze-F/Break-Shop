package com.project.breakshop.service;

import com.project.breakshop.models.entity.Payments;
import com.project.breakshop.models.repository.PaymentsRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;
import org.springframework.boot.test.context.SpringBootTest;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
public class PayServiceTest {

    @Mock
    private PaymentsRepository paymentsRepository;

    @InjectMocks
    private DepositPayService depositPayService;

    @InjectMocks
    private NaverPayService naverPayService;

    @Spy
    private ModelMapper modelMapper;

    @Test
    public void testDepositPayService() {
        depositPayService.pay(1000L, 123L);

        verify(paymentsRepository).save(any(Payments.class));
    }

    @Test
    public void testNaverPayService() {
        naverPayService.pay(2000L, 456L);

        verify(paymentsRepository).save(any(Payments.class));
    }
}
