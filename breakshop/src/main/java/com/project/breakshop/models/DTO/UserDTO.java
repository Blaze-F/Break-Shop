package com.project.breakshop.models.DTO;

import lombok.*;

import javax.persistence.Id;
import java.util.Date;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
@Setter
public class UserDTO {
    @Id
    private Long id;
    //password
    private String email;
    private String name;
    private String phone;
    private String address;
    private String level;

    private Date createdDate;
    private Date modifiedDate;
}
