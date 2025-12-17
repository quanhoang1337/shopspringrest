package com.shop.sukuna.domain.request;

import java.util.List;

import com.shop.sukuna.util.constant.PaymentMethod;

import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ReqOrderDTO {

    private long id;

    private String receiverName;

    private String receiverAddress;

    private String receiverPhone;

    private String paymentMethod;

    private List<ReqOrderItemDTO> orderItems;

    @Getter
    @Setter
    @AllArgsConstructor
    @NoArgsConstructor
    public static class ReqOrderItemDTO {
        private long productId;
        private int quantity;
    }

}
