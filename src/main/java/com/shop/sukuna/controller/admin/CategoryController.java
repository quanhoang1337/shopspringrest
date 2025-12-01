package com.shop.sukuna.controller.admin;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.shop.sukuna.domain.Category;
import com.shop.sukuna.domain.response.pagination.PaginationResponse;
import com.shop.sukuna.service.CategoryService;
import com.shop.sukuna.util.annotation.ApiMessage;
import com.shop.sukuna.util.error.IdInvalidException;
import com.turkraft.springfilter.boot.Filter;

import jakarta.validation.Valid;

@RestController
public class CategoryController {

    private final CategoryService categoryService;

    public CategoryController(CategoryService categoryService) {
        this.categoryService = categoryService;
    }

    // Create category
    @PostMapping("/categories")
    @ApiMessage("Create category")
    public ResponseEntity<Category> createCategory(@Valid @RequestBody Category category) {
        return ResponseEntity.status(HttpStatus.CREATED).body(this.categoryService.createCategory(category));
    }

    // Fetch all categories
    @GetMapping("/categories")
    @ApiMessage("Fetch all categories")
    public ResponseEntity<PaginationResponse> getAllCategories(
            @Filter Specification<Category> spec,
            Pageable pageable) {

        return ResponseEntity.status(HttpStatus.OK).body(this.categoryService.fetchAllCategories(spec, pageable));
    }

    // Fetch product by id
    @GetMapping("/categories/{id}")
    @ApiMessage("Fetch category by id")
    public ResponseEntity<Category> getCategoryById(@PathVariable long id) throws IdInvalidException {

        Category category = this.categoryService.fetchCategoryById(id);
        if (category == null) {
            throw new IdInvalidException("Category với id = " + id + " không tồn tại");
        }

        return ResponseEntity.ok(category);
    }

    // Update a category
    @PutMapping("/categories")
    @ApiMessage("Updated catgory")
    public ResponseEntity<Category> updateUser(@RequestBody Category category)
            throws IdInvalidException {

        Category updatedCategory = this.categoryService.updateCategory(category);

        if (updatedCategory == null) {
            throw new IdInvalidException("Category với id = " + category.getId() + " không tồn tại");
        }

        return ResponseEntity.ok(updatedCategory);
    }

}
