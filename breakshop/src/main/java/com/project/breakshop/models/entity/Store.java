package com.project.breakshop.models.entity;

import com.project.breakshop.models.entity.base.BaseEntity;
import lombok.*;
import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.DynamicInsert;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;
/*
테이블 자동생성 오류로 인해 테이블명을 STORES으로 변경하였음.
 */
@Entity
@AllArgsConstructor(access = AccessLevel.PROTECTED)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Builder
@Getter
@DynamicInsert
@Table(name = "stores")
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

    public enum OpenStatus {
        OPEN,CLOSED
    }

    @Column(nullable = false)
    @Enumerated(EnumType.ORDINAL)
    private OpenStatus openStatus = OpenStatus.CLOSED; //Default Value = CLOSED

    private String introduction;

    @ManyToOne(fetch = FetchType.LAZY)
    User user;

    @ManyToOne(fetch = FetchType.LAZY)
    StoreCategory storeCategory;

    @OneToMany(fetch = FetchType.LAZY)
    List<Order> orderList = new ArrayList<>();

}
