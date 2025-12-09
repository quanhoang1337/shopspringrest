package com.shop.sukuna.controller.user;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.shop.sukuna.domain.Order;
import com.shop.sukuna.domain.Product;
import com.shop.sukuna.domain.response.product.ResProductDTO;
import com.shop.sukuna.repository.OrderRepository;
import com.shop.sukuna.service.OrderService;
import com.shop.sukuna.util.annotation.ApiMessage;

import jakarta.validation.Valid;

@RestController
public class OrderController {

    private final OrderService orderService;
    private final OrderRepository orderRepository;

    OrderController(OrderRepository orderRepository, OrderService orderService) {
        this.orderRepository = orderRepository;
        this.orderService = orderService;
    }

    // Create product
    @PostMapping("/orders")
    @ApiMessage("Create order")
    public ResponseEntity<Order> createOrder(@Valid @RequestBody Order order) {
        return ResponseEntity.status(HttpStatus.CREATED).body(this.orderService.createProduct(product));
    }

}
