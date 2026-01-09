package com.shop.sukuna.domain.request;

import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ReqInventoryDTO {
    @NotNull
    private long productId;
    @NotNull
    private long quantity;
}
