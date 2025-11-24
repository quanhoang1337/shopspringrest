package com.shop.sukuna.service;

import org.springframework.stereotype.Service;

import com.shop.sukuna.domain.Cart;
import com.shop.sukuna.domain.CartDetail;
import com.shop.sukuna.domain.Product;
import com.shop.sukuna.repository.CartDetailRepository;
import com.shop.sukuna.repository.CartRepository;
import com.shop.sukuna.repository.ProductRepository;

import jakarta.servlet.http.HttpSession;

@Service
public class CartService {

    private final UserService userService;
    private final ProductRepository productRepository;
    private final CartRepository cartRepository;
    private final CartDetailRepository cartDetailRepository;

    public CartService(UserService userService, ProductRepository productRepository, CartRepository cartRepository,
            CartDetailRepository cartDetailRepository) {
        this.userService = userService;
        this.productRepository = productRepository;
        this.cartRepository = cartRepository;
        this.cartDetailRepository = cartDetailRepository;
    }

    // public void addProductToCart(String email, long productId, long quantity) {

    // User user = this.userService.getUserByEmail(email);
    // if (user != null) {
    // // check user đã có Cart chưa ? nếu chưa -> tạo mới
    // Cart cart = this.cartRepository.findByUser(user);

    // if (cart == null) {
    // // tạo mới cart
    // Cart otherCart = new Cart();
    // otherCart.setUser(user);
    // otherCart.setSum(0);

    // cart = this.cartRepository.save(otherCart);
    // }

    // // save cart_detail
    // // tìm product by id

    // Optional<Product> productOptional =
    // this.productRepository.findById(productId);
    // if (productOptional.isPresent()) {
    // Product realProduct = productOptional.get();

    // // check sản phẩm đã từng được thêm vào giỏ hàng trước đây chưa ?
    // CartDetail oldDetail = this.cartDetailRepository.findByCartAndProduct(cart,
    // realProduct);
    // //
    // if (oldDetail == null) {
    // CartDetail cd = new CartDetail();
    // cd.setCart(cart);
    // cd.setProduct(realProduct);
    // cd.setPrice(realProduct.getPrice());
    // cd.setQuantity(quantity);
    // this.cartDetailRepository.save(cd);

    // // update cart (sum);
    // int s = cart.getSum() + 1;
    // cart.setSum(s);
    // this.cartRepository.save(cart);
    // session.setAttribute("sum", s);
    // } else {
    // oldDetail.setQuantity(oldDetail.getQuantity() + quantity);
    // this.cartDetailRepository.save(oldDetail);
    // }

    // }

    // }
    // }

}
