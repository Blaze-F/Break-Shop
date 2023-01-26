package com.project.breakshop.controller;


import com.project.breakshop.annotation.LoginCheck;
import com.project.breakshop.annotation.LoginCheck.UserLevel;
import com.project.breakshop.models.DTO.CartItemDTO;
import com.project.breakshop.service.CartService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/users/{userId}/carts")
@RequiredArgsConstructor
public class CartController {

    private final CartService cartService;

    @PostMapping
    @LoginCheck(userLevel = UserLevel.USER)
    public void registerMenuInCart(@PathVariable String userId,
        @Valid @RequestBody CartItemDTO cart) {
        cartService.registerMenuInCart(userId, cart);
    }

    @GetMapping
    @LoginCheck(userLevel = UserLevel.USER)
    public List<CartItemDTO> loadCart(@PathVariable String userId) {
        List<CartItemDTO> cartList = cartService.loadCart(userId);
        return cartList;
    }

    @DeleteMapping
    @LoginCheck(userLevel = UserLevel.USER)
    public void deleteAllMenuInCart(@PathVariable String userId) {
        cartService.deleteAllMenuInCart(userId);
    }

}
