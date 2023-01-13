package com.project.breakshop.accounts.models;

import lombok.*;

import javax.persistence.Entity;


@Entity
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
@Setter
public class UserDTO {


    private int user_id;

    private String email;

    private String user_name;

    private String address;

    private String phone;

}
