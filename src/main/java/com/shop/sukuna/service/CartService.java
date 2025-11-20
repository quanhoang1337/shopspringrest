package com.shop.sukuna.service;

import org.springframework.stereotype.Service;

import com.shop.sukuna.repository.CartDetailRepository;
import com.shop.sukuna.repository.CartRepository;
import com.shop.sukuna.repository.ProductRepository;

@Service
public class CartService {

    private final UserService userService;
    private final ProductRepository productRepository;
    private final CartRepository cartRepository;
    private final CartDetailRepository cartDetailRepository;

    public CartService(UserService userService, ProductRepository productRepository, CartRepository cartRepository,
            CartDetailRepository cartDetailRepository) {
        this.userService = userService;
        this.productRepository = productRepository;
        this.cartRepository = cartRepository;
        this.cartDetailRepository = cartDetailRepository;
    }

    

}
