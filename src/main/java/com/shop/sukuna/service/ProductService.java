package com.shop.sukuna.service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import com.shop.sukuna.domain.Product;
import com.shop.sukuna.domain.User;
import com.shop.sukuna.domain.response.PaginationResponse;
import com.shop.sukuna.domain.response.ResProductDTO;
import com.shop.sukuna.domain.response.ResUserDTO;
import com.shop.sukuna.repository.ProductRepository;

@Service
public class ProductService {

    private final ProductRepository productRepository;

    public ProductService(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    public Product createProduct(Product product) {
        return this.productRepository.save(product);
    }

    public PaginationResponse fetchAllProducts(Specification<Product> spec, Pageable pageable) {
        Page<Product> pageProduct = this.productRepository.findAll(spec, pageable);
        PaginationResponse pr = new PaginationResponse();
        PaginationResponse.Meta mt = new PaginationResponse.Meta();

        mt.setPage(pageable.getPageNumber() + 1);
        mt.setPageSize(pageable.getPageSize());

        mt.setPages(pageProduct.getTotalPages());
        mt.setTotal(pageProduct.getTotalElements());

        pr.setMeta(mt);

        // remove sensitive data
        List<ResProductDTO> listProduct = pageProduct.getContent()
                .stream().map(item -> this.toResProductDTO(item))
                .collect(Collectors.toList());

        pr.setResult(listProduct);

        return pr;
    }

    public Product fetchProductById(long id) {
        Optional<Product> product = this.productRepository.findById(id);
        if (product.isPresent()) {
            return product.get();
        }
        return null;
    }

    public Product updateProduct(Product pr) {
        Optional<Product> product = this.productRepository.findById(pr.getId());
        if (product.isPresent()) {
            Product currentProduct = product.get();
            currentProduct.setName(pr.getName());
            currentProduct.setPrice(pr.getPrice());
            currentProduct.setImage(pr.getImage());
            currentProduct.setDetailDesc(pr.getDetailDesc());
            currentProduct.setShortDesc(pr.getShortDesc());
            return this.productRepository.save(currentProduct);
        }
        return null;
    }

    public void deleteProduct(long id) {
        this.productRepository.deleteById(id);
    }

    public ResProductDTO toResProductDTO(Product product) {
        ResProductDTO res = new ResProductDTO();
        res.setId(product.getId());
        res.setName(product.getName());
        res.setPrice(product.getPrice());
        res.setImage(product.getImage());
        res.setDetailDesc(product.getDetailDesc());
        res.setShortDesc(product.getShortDesc());
        return res;
    }

}
