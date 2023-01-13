package com.project.breakshop.accounts.models.entity;


import lombok.*;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
@AllArgsConstructor(access = AccessLevel.PROTECTED)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Builder
public class User {

    @Id
    private int user_id;

    private String email;

    private String user_name;

    private String address;

    private String phone;

}
