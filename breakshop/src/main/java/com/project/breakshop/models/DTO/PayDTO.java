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

    private final Long id;

    @NotNull
    private final PayType payType;

    @NotNull
    private final Long price;

    @NotNull
    private final LocalDateTime createdAt;

    @NotNull
    private final Long orderId;

    @NotNull
    private final PayStatus status;

    public enum PayType {
        CARD, NAVER_PAY, DEPOSIT
    }

    public enum PayStatus {
        BEFORE_PAY, BEFORE_DEPOSIT, COMPLETE_PAY, FAILURE, CANCALED
    }

}
