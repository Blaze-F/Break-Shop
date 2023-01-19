package com.project.breakshop.models.entity.joinTable;

import com.project.breakshop.models.entity.Menu;
import com.project.breakshop.models.entity.Order;
import lombok.*;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import java.util.HashSet;
import java.util.Set;

@Entity
@AllArgsConstructor(access = AccessLevel.PROTECTED)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Builder
@Getter
public class OrderMenu {
    @Id
    @ManyToOne
    Set<Order> orderSet = new HashSet<>();

    @ManyToOne
    Set<Menu> menuSet = new HashSet<>();

    private int count;
}
