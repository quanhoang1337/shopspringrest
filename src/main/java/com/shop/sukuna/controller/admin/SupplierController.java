package com.shop.sukuna.controller.admin;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.shop.sukuna.domain.Product;
import com.shop.sukuna.domain.Supplier;
import com.shop.sukuna.domain.response.product.ResProductDTO;
import com.shop.sukuna.service.SupplierService;
import com.shop.sukuna.util.annotation.ApiMessage;
import com.shop.sukuna.util.error.IdInvalidException;

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
    public ResponseEntity<Supplier> createSupplier(@Valid @RequestBody Supplier supplier) {
        return ResponseEntity.status(HttpStatus.CREATED).body(this.supplierService.createSupplier(supplier));
    }

    // Update supplier
    @PutMapping("/suppliers")
    @ApiMessage("Update supplier")
    public ResponseEntity<Supplier> updateSupplier(@Valid @RequestBody Supplier supplier) {
        return ResponseEntity.status(HttpStatus.CREATED).body(this.supplierService.updateSupplier(supplier));
    }

    // Fetch product by id
    @GetMapping("/suppliers/{id}")
    @ApiMessage("Fetch supplier by id")
    public ResponseEntity<Supplier> getSupplierById(@PathVariable long id) throws IdInvalidException {

        Supplier supplier = this.supplierService.fetchSupplierById(id);
        if (supplier == null) {
            throw new IdInvalidException("Supplier với id = " + id + " không tồn tại");
        }

        return ResponseEntity.ok(supplier);
    }

}
