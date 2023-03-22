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
public class Menu extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String name;
    private Long price;
    private String photo;
    private String description;

    @OneToMany(fetch = FetchType.LAZY)
    Set<MenuOption> menuOptionSet = new HashSet<>();

    @ManyToOne(fetch = FetchType.LAZY)
    Store store;

}
