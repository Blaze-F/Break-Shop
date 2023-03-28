package com.project.breakshop.models.entity;

import com.project.breakshop.models.entity.base.BaseEntity;
import lombok.*;
import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.DynamicInsert;

import javax.persistence.*;
import java.util.List;

@Entity
@AllArgsConstructor(access = AccessLevel.PROTECTED)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Builder
@Getter
@DynamicInsert
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
    @Column(nullable = false)
    @ColumnDefault("closed")
    private String openStatus;
    private String introduction;

    public void setUser(User user) {
        this.user = user;
    }
    public void setStoreCategory(StoreCategory storeCategory) {
        this.storeCategory = storeCategory;
    }


    @ManyToOne(fetch = FetchType.LAZY)
    User user;

    @ManyToOne(fetch = FetchType.LAZY)
    StoreCategory storeCategory;

    @OneToMany(fetch = FetchType.LAZY)
    List<Order> orderList;

}
