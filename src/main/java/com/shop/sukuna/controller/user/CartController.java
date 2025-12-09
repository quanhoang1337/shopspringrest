package com.shop.sukuna.controller.user;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.shop.sukuna.domain.Cart;
import com.shop.sukuna.domain.request.ReqProductDTO;
import com.shop.sukuna.service.CartService;
import com.shop.sukuna.service.ProductService;
import com.shop.sukuna.util.annotation.ApiMessage;

import jakarta.validation.Valid;

@RestController
public class CartController {

    private final CartService cartService;
    private final ProductService productService;

    public CartController(CartService cartService, ProductService productService) {
        this.cartService = cartService;
        this.productService = productService;
    }

    @PostMapping("/carts")
    @ApiMessage("Add product to cart")
    public ResponseEntity<Cart> addToCart(@Valid @RequestBody ReqProductDTO reqProductDTO) {

        String email = SecurityContextHolder.getContext().getAuthentication().getName();

        long productId = reqProductDTO.getId();

        return ResponseEntity.ok(this.cartService.addProductToCart(email, productId, reqProductDTO.getQuantity()));
    }

}
