package com.shop.sukuna.domain.response;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class RestResponse<T> {

    // Trả về trạng thái và mã lỗi
    private int statusCode;
    private String error;

    // message có thể là string , arraylist
    private Object message;
    private T data;

}
