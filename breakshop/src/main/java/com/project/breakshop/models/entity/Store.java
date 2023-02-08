package com.project.breakshop.models.entity;

import com.project.breakshop.models.entity.base.BaseEntity;
import lombok.*;

import javax.persistence.*;
import java.util.Date;
import java.util.Set;

@Entity
@AllArgsConstructor(access = AccessLevel.PROTECTED)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Builder
@Getter
public class Store extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String name;
    private String phone;
    private String address;
    private String openStatus;
    private String introduction;

    private Date createdDate;
    private Date updatedDate;

    @ManyToOne(fetch = FetchType.LAZY)
    private User user;

    @OneToMany(fetch = FetchType.LAZY)
    Set<StoreCategory> storeCategorySet;

}
