package com.ripple.user_service.response;

import lombok.Getter;

@Getter
public class ApiResponse <T>{
    private String status;
    private String message;
    private T data;

    public ApiResponse(String status, String message, T data){
        this.status = status;
        this.message = message;
        this.data = data;
    }

    public static <T> ApiResponse success(String message, T data){
        return new ApiResponse("success", message, data);
    }
    public static <T> ApiResponse error(String message){
        return new ApiResponse("error", message, null);
    }
}
