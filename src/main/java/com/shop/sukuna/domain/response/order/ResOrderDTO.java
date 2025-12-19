package com.shop.sukuna.domain.response.order;

import java.util.List;

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

    private List<ResOrderItemDTO> orderItems;

    @Getter
    @Setter
    @AllArgsConstructor
    @NoArgsConstructor
    public static class ResOrderItemDTO {
        private long id;
        private int quantity;
        private String image;
    }

}
