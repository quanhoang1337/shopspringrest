package com.shop.sukuna.controller.admin;

import java.util.Optional;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.shop.sukuna.domain.Inventory;
import com.shop.sukuna.domain.Product;
import com.shop.sukuna.repository.ProductRepository;
import com.shop.sukuna.service.InventoryService;
import com.shop.sukuna.util.annotation.ApiMessage;

import jakarta.validation.Valid;

@RestController
public class InventoryController {

    private final InventoryService inventoryService;
    private final ProductRepository productRepository;

    public InventoryController(InventoryService inventoryService, ProductRepository productRepository) {
        this.inventoryService = inventoryService;
        this.productRepository = productRepository;
    }

    // Create inventory
    @PostMapping("/inventories")
    @ApiMessage("Create an inventory")
    public ResponseEntity<Inventory> createInventory(@Valid @RequestBody Inventory inventory) {
        Optional<Product> product = productRepository.findById(inventory.getProduct().getId());
        inventory.setProduct(product.get());
        return ResponseEntity.status(HttpStatus.CREATED).body(this.inventoryService.createInventory(inventory));
    }
}
