package com.project.breakshop.models.entity;

import com.project.breakshop.models.entity.base.BaseEntity;
import lombok.*;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
@AllArgsConstructor(access = AccessLevel.PROTECTED)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@Builder
@Table(name = "MENU")
public class Menu extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "menu_id")
    private Long id;

    @Column(nullable = false)
    private String name;
    @Column(nullable = false)
    private Long price;
    private String photo;
    private String description;

    @OneToMany(fetch = FetchType.LAZY)
    Set<MenuOption> menuOptionSet;

    @ManyToOne(fetch = FetchType.LAZY)
    Store store;

}
