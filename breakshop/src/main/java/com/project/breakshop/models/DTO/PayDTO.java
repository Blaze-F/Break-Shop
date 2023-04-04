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

    public  Long id;

    @NotNull
    public  PayType payType;

    @NotNull
    public  Long price;

    @NotNull
    public  LocalDateTime createdAt;

    @NotNull
    public  Long orderId;

    @NotNull
    public  PayStatus status;

    public enum PayType {
        CARD, NAVER_PAY, DEPOSIT
    }

    public enum PayStatus {
        BEFORE_PAY, BEFORE_DEPOSIT, COMPLETE_PAY, FAILURE, CANCALED
    }

}
