package com.shop.sukuna.domain.response.order;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ResOrderDTO {

    private String image;

    private String receiverName;

    private String receiverAddress;

    private String receiverPhone;

    private String status;

    private String paymentStatus;

    private String paymentMethod;

    private long total;

    @Getter
    @Setter
    @AllArgsConstructor
    @NoArgsConstructor
    public static class OrderItemDTO {
        private long id;
        private int quantity;
    }
    

}
