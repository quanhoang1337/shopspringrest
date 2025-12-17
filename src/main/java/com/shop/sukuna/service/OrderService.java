package com.shop.sukuna.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.shop.sukuna.domain.Cart;
import com.shop.sukuna.domain.CartDetail;
import com.shop.sukuna.domain.Order;
import com.shop.sukuna.domain.Product;
import com.shop.sukuna.domain.User;
import com.shop.sukuna.domain.request.ReqOrderDTO;
import com.shop.sukuna.domain.request.ReqOrderDTO.ReqOrderItemDTO;
import com.shop.sukuna.domain.response.order.ResOrderDTO;
import com.shop.sukuna.domain.response.user.ResCreateUserDTO;
import com.shop.sukuna.repository.CartRepository;
import com.shop.sukuna.repository.ProductRepository;
import com.shop.sukuna.repository.UserRepository;
import com.shop.sukuna.util.SecurityUtil;
import com.shop.sukuna.util.constant.PaymentMethod;

@Service
public class OrderService {

    private final SecurityUtil securityUtil;
    private final CartRepository cartRepository;
    private final UserRepository userRepository;
    private final ProductRepository productRepository;

    public OrderService(SecurityUtil securityUtil, CartRepository cartRepository, UserRepository userRepository,
            ProductRepository productRepository) {
        this.securityUtil = securityUtil;
        this.cartRepository = cartRepository;
        this.userRepository = userRepository;
        this.productRepository = productRepository;
    }

    public ResOrderDTO placeOrder(ReqOrderDTO reqOrderDTO) {

        String userName = SecurityUtil.getCurrentUserLogin().orElse(null);
        User user = this.userRepository.findByEmail(userName);
        Cart cart = this.cartRepository.findByUser(user);

        // Cách làm cũ là lấy id trong request được gửi lên nhưng ko thể lấy ra quantity
        // vì phải lấy theo reqOrderDTO
        // Lấy ra từng id product trong request được gửi lên sau đó gom thành List
        // List<Long> ids = reqOrderDTO.getOrderItems().stream()
        // .map(ReqOrderItemDTO::getProductId)
        // .toList();
        // List<Product> lists = this.productRepository.findByIdIn(ids);

        Map<Long, Integer> quantityMap = reqOrderDTO.getOrderItems().stream()
                .collect(Collectors.toMap(
                        ReqOrderItemDTO::getProductId,
                        ReqOrderItemDTO::getQuantity));

        List<Product> lists = productRepository.findByIdIn(quantityMap.keySet());

        List<ResOrderDTO.ResOrderItemDTO> resItems = lists.stream()
                .map(p -> new ResOrderDTO.ResOrderItemDTO(
                        p.getId(),
                        quantityMap.get(p.getId()) // ✅ quantity lấy đúng chỗ
                ))
                .toList();

        // Xử lý cart bằng null thì sẽ như thế nào ? nghĩ chưa ra
        // if (cart == null) {

        // }
        List<CartDetail> cartDetails = cart == null ? new ArrayList<CartDetail>() : cart.getCartDetails();
        long totalPrice = 0;
        for (CartDetail cd : cartDetails) {
            totalPrice += cd.getPrice() * cd.getQuantity();
        }

        ResOrderDTO resOrderDTO = new ResOrderDTO();
        ResOrderDTO.ResOrderItemDTO com = new ResOrderDTO.ResOrderItemDTO();
        for (Product convertResOrderDTO : lists) {
            resOrderDTO.setImage(convertResOrderDTO.getImage());
            resOrderDTO.setReceiverName(reqOrderDTO.getReceiverName());
            resOrderDTO.setReceiverAddress(reqOrderDTO.getReceiverAddress());
            resOrderDTO.setReceiverPhone(reqOrderDTO.getReceiverPhone());
            resOrderDTO.setStatus("SHIPPING");
            resOrderDTO.setPaymentStatus("PENDING");
            resOrderDTO.setPaymentMethod("BANKING");
            resOrderDTO.setTotal(totalPrice);
            resOrderDTO.setOrderItems(resItems);
        }

        return resOrderDTO;
    }

    public boolean isValidPayment(String method) {
        try {
            PaymentMethod.valueOf(method.toUpperCase());
            return true;
        } catch (IllegalArgumentException e) {
            return false;
        }
    }

}
