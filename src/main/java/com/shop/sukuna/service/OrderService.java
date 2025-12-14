package com.shop.sukuna.service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.shop.sukuna.domain.Cart;
import com.shop.sukuna.domain.CartDetail;
import com.shop.sukuna.domain.Order;
import com.shop.sukuna.domain.Product;
import com.shop.sukuna.domain.User;
import com.shop.sukuna.domain.request.ReqOrderDTO;
import com.shop.sukuna.domain.request.ReqOrderDTO.OrderItemDTO;
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

        // reqOrderDTO.getOrderItems();

        List<Product> product = this.productRepository.findAll();

        // lỗi ngay đây , chưa tìm ra , xem lại OrderItemDTO
        List<Long> ids = reqOrderDTO.getOrderItems().stream()
                .map(OrderItemDTO::getId)
                .toList();

        List<Long> test = List.of(1L, 5L, 7L);

        List<Product> productTest = this.productRepository.findByIdIn(ids);

        /*
         * Chuyển cái list này thành DTO ====> trả ra list , ko trả ra nguyên cái
         * product
         */

        // Viết lambda , gom hết toàn bộ thành list rồi phân rã từng phần tử ----> set
        // ra DTO

        List<Product> lists = this.productRepository.findByIdIn(ids);

        // List<Object> reqSkills = (x) -> ()
        // .stream().map(x -> x.getId())
        // .collect(Collectors.toList());

        // Xử lý cart bằng null thì sẽ như thế nào ? nghĩ chưa ra
        // if (cart == null) {

        // }

        List<CartDetail> cartDetails = cart == null ? new ArrayList<CartDetail>() : cart.getCartDetails();
        long totalPrice = 0;
        for (CartDetail cd : cartDetails) {
            totalPrice += cd.getPrice() * cd.getQuantity();
        }

        ResOrderDTO resOrderDTO = new ResOrderDTO();
        ResOrderDTO.OrderItemDTO com = new ResOrderDTO.OrderItemDTO();
        for (Product convertReqOrderDTO : lists) {
            resOrderDTO.setImage(convertReqOrderDTO.getImage());
            resOrderDTO.setReceiverName(reqOrderDTO.getReceiverName());
            resOrderDTO.setReceiverAddress(reqOrderDTO.getReceiverAddress());
            resOrderDTO.setReceiverPhone(reqOrderDTO.getReceiverPhone());
            resOrderDTO.setStatus("SHIPPING");
            resOrderDTO.setPaymentStatus("PENDING");
            resOrderDTO.setPaymentMethod("BANKING");
            resOrderDTO.setTotal(totalPrice);
            com.setId(convertReqOrderDTO.getId());
            com.setQuantity(5);
        }

        // // Đã trả ra như yêu cầu , giờ chỉ cần trả ra id , product client đã mua là OK !

        

        // ReqOrderDTO.OrderItemDTO orderItemDTO = new ReqOrderDTO.OrderItemDTO();

        // resOrderDTO.setReceiverName(user.getName());
        // resOrderDTO.setReceiverAddress(user.getAddress());
        // resOrderDTO.setReceiverPhone(reqOrderDTO.getReceiverPhone());
        // resOrderDTO.setStatus(reqOrderDTO.getPaymentMethod());
        // resOrderDTO.setPaymentMethod(reqOrderDTO.getPaymentMethod());
        // resOrderDTO.setOrderItems(lists);
        // resOrderDTO.setOrderItems(lists);

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
