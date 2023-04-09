package com.project.breakshop.models.entity;


import com.project.breakshop.annotation.LoginCheck;
import com.project.breakshop.models.entity.base.BaseEntity;
import lombok.*;
import javax.persistence.*;
import java.io.Serializable;
import java.util.LinkedList;
import java.util.List;

@Entity
@AllArgsConstructor(access = AccessLevel.PROTECTED)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Builder
@Getter
@Table( indexes = @Index(name = "i_email", columnList = "email"))
public class User extends BaseEntity implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    //password
    @Column(nullable = false)
    private String password;
    @Column(unique = true,nullable = false)
    private String email;

    private String name;
    private String phone;
    private String address;

    @Enumerated(EnumType.ORDINAL)
    private LoginCheck.UserLevel level;

    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "order_id")
    private List<Order> orderList;
}