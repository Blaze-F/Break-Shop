package com.project.breakshop.models.entity;

import com.project.breakshop.models.entity.joinTable.OrderMenuOption;
import lombok.*;

import javax.persistence.*;

@Entity
@AllArgsConstructor(access = AccessLevel.PROTECTED)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Builder
@Getter
public class MenuOption {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String name;
    private Long price;

    @OneToMany(fetch = FetchType.LAZY)
    private OrderMenuOption orderMenuOption;

    @ManyToOne(fetch = FetchType.LAZY)
    private Menu menu;


}
