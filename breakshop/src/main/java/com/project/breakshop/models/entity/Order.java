package com.project.breakshop.models.entity;

import com.project.breakshop.models.entity.base.BaseEntity;
import com.project.breakshop.models.entity.joinTable.OrderMenuOption;
import lombok.*;

import javax.persistence.*;
import java.util.Set;

@Entity
@AllArgsConstructor(access = AccessLevel.PROTECTED)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Builder
@Getter
public class Order extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Enumerated(EnumType.STRING)
    private OrderStatus orderStatus;

    private String address;
    private Long totalPrice;

    @ManyToOne(fetch = FetchType.LAZY)
    private User user;

    @OneToMany(fetch = FetchType.LAZY)
    Set<Payments> payments;

    @OneToMany(fetch = FetchType.LAZY)
    Set<OrderMenuOption> orderMenuOptions;

    public enum OrderStatus {
        BEFORE_ORDER, COMPLETE_ORDER, APPROVED_ORDER, DELIVERING, COMPLETE_DELIVERY
    };
}
