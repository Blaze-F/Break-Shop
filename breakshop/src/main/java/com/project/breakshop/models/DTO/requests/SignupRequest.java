package com.project.breakshop.models.DTO.requests;

import com.project.breakshop.annotation.LoginCheck;
import lombok.*;

import javax.validation.constraints.NotNull;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class SignupRequest {
    @NotNull
    private String email;
    private String password;

    private String name;
    private String phone;
    private String address;

    private LoginCheck.UserLevel level;
}
