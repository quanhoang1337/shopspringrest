package com.shop.sukuna.controller.user;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import com.shop.sukuna.domain.Cart;
import com.shop.sukuna.service.CartService;
import com.shop.sukuna.util.annotation.ApiMessage;
import com.shop.sukuna.util.error.IdInvalidException;

@RestController
public class CartController {

    private final CartService cartService;

    public CartController(CartService cartService) {
        this.cartService = cartService;
    }

    @PostMapping("/api/cart/items")
    @ApiMessage("Create a cart")
    public ResponseEntity<Cart> addToCart() {
        
    }

}
