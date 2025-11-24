package com.shop.sukuna.controller.user;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.shop.sukuna.domain.Cart;
import com.shop.sukuna.domain.Product;
import com.shop.sukuna.service.CartService;
import com.shop.sukuna.service.ProductService;
import com.shop.sukuna.util.annotation.ApiMessage;

import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;

@RestController
public class CartController {

    private final CartService cartService;
    private final ProductService productService;

    public CartController(CartService cartService, ProductService productService) {
        this.cartService = cartService;
        this.productService = productService;
    }

    // @PostMapping("/cart")
    // @ApiMessage("Add product to cart")
    // public ResponseEntity<Cart> addToCart(@PathVariable long id ,@Valid @RequestBody ...) {

    // String email =
    // SecurityContextHolder.getContext().getAuthentication().getName();

    // Product product = this.productService.fetchProductById(id);

    // long productId = id;

    // this.cartService.addProductToCart(email, productId, 1);

    // return "redirect:/";
    // }

}
