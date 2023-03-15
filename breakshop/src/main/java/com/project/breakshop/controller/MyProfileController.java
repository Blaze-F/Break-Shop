package com.project.breakshop.controller;


import com.project.breakshop.annotation.CurrentUserId;
import com.project.breakshop.annotation.LoginCheck;
import com.project.breakshop.service.LoginService;
import com.project.breakshop.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import static com.project.breakshop.utils.ResponseEntityConstants.RESPONSE_OK;

@Tag(name = "/my-profiles", description = "프로필 정보 관련 API")
@RestController
@RequestMapping("/my-profiles")
@RequiredArgsConstructor
public class MyProfileController {

    private final LoginService loginService;
    private final UserService userService;

    @DeleteMapping
    @LoginCheck(userLevel = LoginCheck.UserLevel.USER)
    @Operation(summary = "회원 탈퇴", description = "회원을 탈퇴시키고, 세션에서 삭제합니다.")
    public ResponseEntity<Void> deleteUser(@CurrentUserId String userId) {
        userService.deleteUser(userId);
        loginService.logoutUser();
        return RESPONSE_OK;
    }

    @PatchMapping("/password")
    @LoginCheck(userLevel = LoginCheck.UserLevel.USER)
    @Operation(summary = "비밀번호 변경", description = "유저의 비밀번호를 변경합니다.")
    public ResponseEntity<Void> changeUserPassword(@CurrentUserId String userId,
        String password) {
        userService.changeUserPassword(userId, password);
        return RESPONSE_OK;
    }

}
