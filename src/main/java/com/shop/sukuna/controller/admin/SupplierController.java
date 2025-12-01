package com.shop.sukuna.controller.admin;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.shop.sukuna.domain.Product;
import com.shop.sukuna.domain.Supplier;
import com.shop.sukuna.service.SupplierService;
import com.shop.sukuna.util.annotation.ApiMessage;

import jakarta.validation.Valid;

@RestController
public class SupplierController {

    private final SupplierService supplierService;

    public SupplierController(SupplierService supplierService) {
        this.supplierService = supplierService;
    }

    // Create supplier
    @PostMapping("/suppliers")
    @ApiMessage("Create supplier")
    public ResponseEntity<Supplier> createProduct(@Valid @RequestBody Supplier supplier) {
        return ResponseEntity.status(HttpStatus.CREATED).body(this.supplierService.createSupplier(supplier));
    }

}
