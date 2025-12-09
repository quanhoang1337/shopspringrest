package com.shop.sukuna.controller.admin;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.shop.sukuna.domain.Product;
import com.shop.sukuna.domain.response.pagination.PaginationResponse;
import com.shop.sukuna.domain.response.product.ResProductDTO;
import com.shop.sukuna.service.ProductService;
import com.shop.sukuna.util.annotation.ApiMessage;
import com.shop.sukuna.util.error.IdInvalidException;
import com.turkraft.springfilter.boot.Filter;

import jakarta.validation.Valid;

@RestController
public class ProductController {

    private final ProductService productService;

    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    // Create product
    @PostMapping("/products")
    @ApiMessage("Create product")
    public ResponseEntity<ResProductDTO> createProduct(@Valid @RequestBody Product product) {
        return ResponseEntity.status(HttpStatus.CREATED).body(this.productService.createProduct(product));
    }

    // Fetch product
    @GetMapping("/products")
    @ApiMessage("Fetch products")
    public ResponseEntity<PaginationResponse> getAllProducts(
            @Filter Specification<Product> spec,
            Pageable pageable) {

        return ResponseEntity.status(HttpStatus.OK).body(this.productService.fetchAllProducts(spec, pageable));
    }

    // Fetch product by id
    @GetMapping("/products/{id}")
    @ApiMessage("Fetch product by id")
    public ResponseEntity<ResProductDTO> getProductById(@PathVariable long id) throws IdInvalidException {

        ResProductDTO product = this.productService.fetchProductById(id);
        if (product == null) {
            throw new IdInvalidException("Product với id = " + id + " không tồn tại");
        }

        return ResponseEntity.ok(product);
    }

    // Update product
    @PutMapping("/products")
    public ResponseEntity<ResProductDTO> updateProduct(@Valid @RequestBody Product product) {
        ResProductDTO updatedProduct = this.productService.updateProduct(product);
        return ResponseEntity.ok(updatedProduct);
    }

    // Delete product
    @DeleteMapping("/products/{id}")
    public ResponseEntity<Void> deleteProduct(@PathVariable("id") long id) {
        this.productService.deleteProduct(id);
        return ResponseEntity.ok(null);

    }
}
