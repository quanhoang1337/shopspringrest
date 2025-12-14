package com.shop.sukuna.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import com.shop.sukuna.domain.Cart;
import com.shop.sukuna.domain.Order;
import com.shop.sukuna.domain.User;

@Repository
public interface OrderRepository extends JpaRepository<Order, Long>, JpaSpecificationExecutor<Order> {

    List<Order> findByUser(User user);

    

}
