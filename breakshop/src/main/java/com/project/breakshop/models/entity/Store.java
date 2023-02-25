package com.project.breakshop.models.entity;

import com.project.breakshop.models.entity.base.BaseEntity;
import lombok.*;

import javax.persistence.*;
import java.util.Date;
import java.util.List;
import java.util.Set;

@Entity
@AllArgsConstructor(access = AccessLevel.PROTECTED)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Builder
@Getter
public class Store extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "store_id")
    private Long id;

    @Column(nullable = false)
    private String name;
    private String phone;
    @Column(nullable = false)
    private String address;
    @Column(nullable = false,columnDefinition = "closed")
    private String openStatus;
    private String introduction;


    @ManyToOne(fetch = FetchType.LAZY)
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    StoreCategory storeCategory;

    @OneToMany(fetch = FetchType.LAZY)
    List<Order> orderList;

}
