package com.shop.sukuna.service;

import java.util.Optional;

import org.springframework.stereotype.Service;

import com.shop.sukuna.domain.Cart;
import com.shop.sukuna.domain.CartDetail;
import com.shop.sukuna.domain.Product;
import com.shop.sukuna.domain.User;
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

    public Cart addProductToCart(String email, long productId, long quantity) {

        User user = this.userService.getUserByUsername(email);

        // check user đã có Cart chưa ?
        // nếu chưa -> tạo mới
        Cart cart = this.cartRepository.findByUser(user);

        if (user != null) {

            if (cart == null) {
                // tạo mới cart
                Cart newCart = new Cart();
                newCart.setUser(user);
                newCart.setItemCount(0);

                cart = this.cartRepository.save(newCart);
            }

            // save cart_detail
            // find product by id

            Optional<Product> productOptional = this.productRepository.findById(productId);
            if (productOptional.isPresent()) {
                Product existProduct = productOptional.get();

                // check sản phẩm đã từng được thêm vào giỏ hàng trước đây chưa ?
                CartDetail existCartDetail = this.cartDetailRepository.findByCartAndProduct(cart, existProduct);

                if (existCartDetail == null) {
                    CartDetail cartDetail = new CartDetail();
                    cartDetail.setCart(cart);
                    cartDetail.setProduct(existProduct);
                    cartDetail.setPrice(existProduct.getPrice());
                    cartDetail.setQuantity(quantity);
                    this.cartDetailRepository.save(cartDetail);

                    // update cart (total item count (the number of product in cart));
                    int totalQuantity = cart.getItemCount() + 1;
                    cart.setItemCount(totalQuantity);
                    ;
                    this.cartRepository.save(cart);
                } else {
                    existCartDetail.setQuantity(existCartDetail.getQuantity() + quantity);
                    this.cartDetailRepository.save(existCartDetail);
                }
            }
        }
        return cart;
    }

}
