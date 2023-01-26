package com.project.breakshop.service;

import com.flab.makedel.dao.CartItemDAO;
import com.flab.makedel.dto.CartItemDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CartService {

    private final CartItemDAO cartItemDAO;

    public List<CartItemDTO> loadCart(String userId) {
        return cartItemDAO.selectCartList(userId);
    }

    public void registerMenuInCart(String userId, CartItemDTO cart) {
        cartItemDAO.insertMenu(userId, cart);
    }

    public void deleteAllMenuInCart(String userId) {
        cartItemDAO.deleteMenuList(userId);
    }

}
