package com.project.breakshop.models.DTO;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;

@Getter
@AllArgsConstructor
@Builder
@Setter
public class PayDTO {

    private  Long id;

    @NotNull
    private  PayType payType;

    @NotNull
    private  Long price;

    @NotNull
    private  LocalDateTime createdAt;

    @NotNull
    private  Long orderId;

    @NotNull
    private  PayStatus status;

    public enum PayType {
        CARD, NAVER_PAY, DEPOSIT
    }

    public enum PayStatus {
        BEFORE_PAY, BEFORE_DEPOSIT, COMPLETE_PAY, FAILURE, CANCALED
    }

}
