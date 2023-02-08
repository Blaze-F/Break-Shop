package com.project.breakshop.models.entity.joinTable;

import com.project.breakshop.models.entity.MenuOption;
import com.project.breakshop.models.entity.Order;
import lombok.*;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

@Entity
@AllArgsConstructor(access = AccessLevel.PROTECTED)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Builder
@Getter
public class OrderMenuOption {

    @ManyToOne
    MenuOption menuOption;

    @ManyToOne
    Order order;
}
