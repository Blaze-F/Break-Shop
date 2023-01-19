package com.project.breakshop.models.entity;


import com.project.breakshop.models.entity.base.BaseEntity;
import lombok.*;

import javax.persistence.*;

@Entity
@AllArgsConstructor(access = AccessLevel.PROTECTED)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Builder
@Getter
public class User extends BaseEntity {
    @Id
    private Long id;
    //password
    private String email;
    private String name;
    private String phone;
    private String address;
    private String level;

    @OneToMany
    private Order order;
}
