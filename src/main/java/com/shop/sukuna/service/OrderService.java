package com.shop.sukuna.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.shop.sukuna.domain.Cart;
import com.shop.sukuna.domain.CartDetail;
import com.shop.sukuna.domain.Inventory;
import com.shop.sukuna.domain.Order;
import com.shop.sukuna.domain.Product;
import com.shop.sukuna.domain.User;
import com.shop.sukuna.domain.request.ReqOrderDTO;
import com.shop.sukuna.domain.request.ReqOrderDTO.ReqOrderItemDTO;
import com.shop.sukuna.domain.response.order.ResOrderDTO;
import com.shop.sukuna.repository.CartRepository;
import com.shop.sukuna.repository.InventoryRepository;
import com.shop.sukuna.repository.OrderRepository;
import com.shop.sukuna.repository.ProductRepository;
import com.shop.sukuna.repository.UserRepository;
import com.shop.sukuna.util.SecurityUtil;
import com.shop.sukuna.util.constant.PaymentMethod;

@Service
public class OrderService {

    private final CartRepository cartRepository;
    private final UserRepository userRepository;
    private final OrderRepository orderRepository;
    private final ProductRepository productRepository;
    private final InventoryRepository inventoryRepository;

    public OrderService(CartRepository cartRepository, UserRepository userRepository,
            ProductRepository productRepository, OrderRepository orderRepository,
            InventoryRepository inventoryRepository) {
        this.cartRepository = cartRepository;
        this.userRepository = userRepository;
        this.orderRepository = orderRepository;
        this.productRepository = productRepository;
        this.inventoryRepository = inventoryRepository;
    }

    public ResOrderDTO placeOrder(ReqOrderDTO reqOrderDTO) {

        String userName = SecurityUtil.getCurrentUserLogin().orElse(null);
        User user = this.userRepository.findByEmail(userName);
        Cart cart = this.cartRepository.findByUser(user);

        Map<Long, Integer> quantityMap = reqOrderDTO.getOrderItems().stream()
                .collect(Collectors.toMap(
                        ReqOrderItemDTO::getProductId,
                        ReqOrderItemDTO::getQuantity));

        List<Product> lists = productRepository.findByIdIn(quantityMap.keySet());

        List<ResOrderDTO.ResOrderItemDTO> resItems = lists.stream()
                .map(p -> new ResOrderDTO.ResOrderItemDTO(
                        p.getId(),
                        quantityMap.get(p.getId()), // quantity lấy đúng chỗ
                        p.getImage()))
                .toList();

        /*
         * Xử lý cart bằng null thì sẽ như thế nào ? nghĩ chưa ra
         * if (cart == null) {
         * }
         */

        List<CartDetail> cartDetails = cart == null ? new ArrayList<CartDetail>() : cart.getCartDetails();
        long totalPrice = 0;
        for (CartDetail cd : cartDetails) {
            totalPrice += cd.getPrice() * cd.getQuantity();
        }

        // Lấy ra quantity đã đặt , trừ thẳng vào inventory của mỗi product , save xuống
        // DB
        // Giải quyết trường hợp inventory ko đủ số lượng order gửi (inventory âm hoặc ko đủ để client đặt)
        for (Product product : lists) {
            Integer orderQuantity = quantityMap.get(product.getId());
            Inventory inventory = this.inventoryRepository.findByProductId(product.getId());
            inventory.setQuantity(inventory.getQuantity() - orderQuantity);
            inventoryRepository.save(inventory);
        }

        // Xử lý nếu client dùng PaymentMethod khác thì sẽ như thế nào (COD , BANKING)
        ResOrderDTO resOrderDTO = new ResOrderDTO();
        resOrderDTO.setReceiverName(reqOrderDTO.getReceiverName());
        resOrderDTO.setReceiverAddress(reqOrderDTO.getReceiverAddress());
        resOrderDTO.setReceiverPhone(reqOrderDTO.getReceiverPhone());
        resOrderDTO.setStatus("SHIPPING");
        resOrderDTO.setPaymentStatus("PENDING");
        resOrderDTO.setPaymentMethod("BANKING");
        resOrderDTO.setTotal(totalPrice);
        resOrderDTO.setOrderItems(resItems);

        Order order = new Order();
        order.setTotal(totalPrice);
        order.setReceiverName(user.getName());
        order.setReceiverAddress(reqOrderDTO.getReceiverName());
        order.setReceiverPhone(reqOrderDTO.getReceiverPhone());
        order.setStatus("SHIPPING");
        order.setPaymentStatus("PENDING");
        order.setPaymentMethod("BANKING");
        order.setUser(user);
        this.orderRepository.save(order);

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
