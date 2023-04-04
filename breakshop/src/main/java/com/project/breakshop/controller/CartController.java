package com.project.breakshop.controller;


import com.project.breakshop.models.DTO.CartItemDTO;
import com.project.breakshop.service.CartService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;


@Tag(name = "/users/{userId}/carts", description = "cart API")
@RestController
@RequestMapping("/users/{userId}/carts")
@RequiredArgsConstructor
public class CartController {

    private final CartService cartService;


    @PostMapping
    @Operation(summary = "유저의 메뉴를 카트에 추가")
    public void registerMenuInCart(@PathVariable String userId,
                                   @Valid @RequestBody CartItemDTO cart) {
        cartService.registerMenuInCart(userId, cart);
    }

    @GetMapping
    @Operation(summary = "유저의 카트 리스트 로딩")
    public List<CartItemDTO> loadCart(@PathVariable String userId) {
        List<CartItemDTO> cartList = cartService.loadCart(userId);
        return cartList;
    }

    @DeleteMapping
    @Operation(summary = "")
    public void deleteAllMenuInCart(@PathVariable String userId) {
        cartService.deleteAllMenuInCart(userId);
    }

}
