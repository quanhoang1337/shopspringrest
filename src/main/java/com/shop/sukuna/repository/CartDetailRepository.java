package com.shop.sukuna.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import com.shop.sukuna.domain.Cart;
import com.shop.sukuna.domain.CartDetail;
import com.shop.sukuna.domain.Product;

@Repository
public interface CartDetailRepository extends JpaRepository<CartDetail, Long>, JpaSpecificationExecutor<CartDetail> {

    CartDetail findByCartAndProduct(Cart cart, Product product);
}
