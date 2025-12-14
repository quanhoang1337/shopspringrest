package com.shop.sukuna.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import com.shop.sukuna.domain.Product;
import com.shop.sukuna.domain.request.ReqOrderDTO;
import com.shop.sukuna.domain.request.ReqProductDTO;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long>, JpaSpecificationExecutor<Product> {
    List<Product> findAll();

    List<Product> findByIdIn(List<Long> id);
}
