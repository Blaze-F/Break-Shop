package com.project.breakshop.models.DTO;


import com.project.breakshop.annotation.LoginCheck;
import lombok.*;

import javax.validation.constraints.NotNull;
import java.util.Date;


/*
@Builder는 DTO 생성 시, 빌더 클래스를 자동으로 추가해준다. 빌더 클래스는 멤버변수별 메소드가 있고
이는 변수에 값을 set하고 이값을 통하여 build() 메소드를 통해 멤버변수에 필수값들을 null체크하고
이 멤버변수값을 이용해 빌더 클래스의 생성자를 호출하고 인스턴스를 리턴한다.
*/

@Getter
@AllArgsConstructor
@Builder
@Setter
@NoArgsConstructor
public class UserDTO {

    public Long id;
    public String password;

    @NotNull
    public String email;

    public String name;

    public String phone;

    public String address;

    public LoginCheck.UserLevel level;

    public Date createdAt;

    public Date updatedAt;
}
