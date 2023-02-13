package com.project.breakshop.models.entity.joinTable;

import com.project.breakshop.models.entity.Menu;
import com.project.breakshop.models.entity.Order;
import lombok.*;

import javax.persistence.*;

@Entity
@AllArgsConstructor(access = AccessLevel.PROTECTED)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Builder
@Getter
public class OrderMenu {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    Long id;
    @ManyToOne
    Order order;

    @ManyToOne
    Menu menu;

    private int count;
}
