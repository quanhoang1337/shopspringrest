package com.shop.sukuna.service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import com.shop.sukuna.domain.Category;
import com.shop.sukuna.domain.Product;
import com.shop.sukuna.domain.User;
import com.shop.sukuna.domain.response.pagination.PaginationResponse;
import com.shop.sukuna.domain.response.product.ResProductDTO;
import com.shop.sukuna.repository.CategoryRepository;

@Service
public class CategoryService {

    private final CategoryRepository categoryRepository;

    public CategoryService(CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }

    public Category createCategory(Category category) {
        return this.categoryRepository.save(category);
    }

    public PaginationResponse fetchAllCategories(Specification<Category> spec, Pageable pageable) {
        Page<Category> pageCategory = this.categoryRepository.findAll(spec, pageable);
        PaginationResponse pr = new PaginationResponse();
        PaginationResponse.Meta mt = new PaginationResponse.Meta();

        mt.setPage(pageable.getPageNumber() + 1);
        mt.setPageSize(pageable.getPageSize());

        mt.setPages(pageCategory.getTotalPages());
        mt.setTotal(pageCategory.getTotalElements());

        pr.setMeta(mt);
        pr.setResult(pageCategory.getContent());

        return pr;
    }

    public Category fetchCategoryById(long id) {
        Optional<Category> category = this.categoryRepository.findById(id);
        if (category.isPresent()) {
            return category.get();
        }
        return null;
    }

    public Category updateCategory(Category category) {
        Category currentCategory = this.fetchCategoryById(category.getId());
        if (currentCategory != null) {
            currentCategory.setName(category.getName());
            currentCategory.setDescription(category.getDescription());

            this.categoryRepository.save(currentCategory);
        }
        return currentCategory;
    }

}
