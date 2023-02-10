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
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "user_id")
    private Long id;
    //password
    @Column(nullable = false)
    private String password;
    @Column(unique = true,nullable = false)
    private String email;

    private String name;
    private String phone;
    private String address;
    private String level;

    @OneToMany(fetch = FetchType.LAZY)
    private Order order;
}
