package com.project.breakshop.models.entity;

import com.project.breakshop.models.entity.base.BaseEntity;
import lombok.*;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.OneToMany;
import java.util.HashSet;
import java.util.Set;

@Entity
@AllArgsConstructor(access = AccessLevel.PROTECTED)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Builder
@Getter
public class Menu extends BaseEntity {
    @Id
    private Long id;

    private String name;
    private Long price;
    private Lob photo; //TODO
    private String description;

    @OneToMany
    Set<MenuOption> menuOptionSet = new HashSet<>();

}
