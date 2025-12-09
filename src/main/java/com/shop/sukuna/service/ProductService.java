package com.shop.sukuna.service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import com.shop.sukuna.domain.Product;
import com.shop.sukuna.domain.response.pagination.PaginationResponse;
import com.shop.sukuna.domain.response.product.ResProductDTO;
import com.shop.sukuna.repository.ProductRepository;

@Service
public class ProductService {

    private final ProductRepository productRepository;

    public ProductService(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    public ResProductDTO createProduct(Product product) {
        this.productRepository.save(product);

        // convert to response
        ResProductDTO resProductDTO = new ResProductDTO();
        resProductDTO.setId(product.getId());
        resProductDTO.setName(product.getName());
        resProductDTO.setPrice(product.getPrice());
        resProductDTO.setImage(product.getImage());
        resProductDTO.setDetailDesc(product.getDetailDesc());
        resProductDTO.setShortDesc(product.getShortDesc());
        resProductDTO.setCreatedAt(product.getCreatedAt());
        resProductDTO.setCreatedBy(product.getCreatedBy());
        resProductDTO.setCategoryName(product.getCategory().getName());
        resProductDTO.setSupplierName(product.getSupplier().getSupplierName());

        return resProductDTO;

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
                .stream().map(item -> this.convertToResProductDTO(item))
                .collect(Collectors.toList());

        pr.setResult(listProduct);

        return pr;
    }

    public ResProductDTO fetchProductById(long id) {
        Optional<Product> product = this.productRepository.findById(id);
        if (product.isPresent()) {
            return this.convertToResProductDTO(product.get());
        }
        return null;
    }

    public ResProductDTO updateProduct(Product pr) {
        Optional<Product> product = this.productRepository.findById(pr.getId());
        if (product.isPresent()) {
            Product currentProduct = product.get();
            currentProduct.setName(pr.getName());
            currentProduct.setPrice(pr.getPrice());
            currentProduct.setImage(pr.getImage());
            currentProduct.setDetailDesc(pr.getDetailDesc());
            currentProduct.setShortDesc(pr.getShortDesc());
            this.productRepository.save(currentProduct);

            // convert response
            return this.convertToResProductDTO(currentProduct);
        }

        return null;
    }

    public void deleteProduct(long id) {
        this.productRepository.deleteById(id);
    }

    public ResProductDTO convertToResProductDTO(Product product) {
        ResProductDTO resProductDTO = new ResProductDTO();
        resProductDTO.setId(product.getId());
        resProductDTO.setName(product.getName());
        resProductDTO.setPrice(product.getPrice());
        resProductDTO.setImage(product.getImage());
        resProductDTO.setDetailDesc(product.getDetailDesc());
        resProductDTO.setShortDesc(product.getShortDesc());
        resProductDTO.setCreatedAt(product.getCreatedAt());
        resProductDTO.setCreatedBy(product.getCreatedBy());
        resProductDTO.setCategoryName(product.getCategory().getName());
        resProductDTO.setSupplierName(product.getSupplier().getSupplierName());
        return resProductDTO;
    }

}
