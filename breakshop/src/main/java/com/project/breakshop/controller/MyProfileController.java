package com.project.breakshop.controller;


import com.project.breakshop.annotation.CurrentUserId;
import com.project.breakshop.annotation.LoginCheck;
import com.project.breakshop.service.LoginService;
import com.project.breakshop.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import static com.project.breakshop.utils.ResponseEntityConstants.RESPONSE_OK;


@RestController
@RequestMapping("/my-profiles")
@RequiredArgsConstructor
public class MyProfileController {

    private final LoginService loginService;
    private final UserService userService;

    @DeleteMapping
    @LoginCheck(userLevel = LoginCheck.UserLevel.USER)
    public ResponseEntity<Void> deleteUser(@CurrentUserId String userId) {
        userService.deleteUser(userId);
        loginService.logoutUser();
        return RESPONSE_OK;
    }

    @PatchMapping("/password")
    @LoginCheck(userLevel = LoginCheck.UserLevel.USER)
    public ResponseEntity<Void> changeUserPassword(@CurrentUserId String userId,
        String password) {
        userService.changeUserPassword(userId, password);
        return RESPONSE_OK;
    }

}
