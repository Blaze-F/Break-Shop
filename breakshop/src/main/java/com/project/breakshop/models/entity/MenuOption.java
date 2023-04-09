package com.project.breakshop.models.entity;

import com.project.breakshop.models.entity.joinTable.OrderMenuOption;
import lombok.*;

import javax.persistence.*;
import java.util.Set;

@Entity
@AllArgsConstructor(access = AccessLevel.PROTECTED)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Builder
@Getter
public class MenuOption {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "menu_option_id")
    private Long MenuOptionId;
    private String name;
    private Long price;

    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
            @JoinColumn(name = "order_menu_option_id")
    Set<OrderMenuOption> orderMenuOptionSet;

    @ManyToOne(fetch = FetchType.LAZY)
    private Menu menu;


}
