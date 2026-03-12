package com.ripple.user_service.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserRequest {
   private String email;
    private String password;
    private String username;

    public UserRequest(){};
    public UserRequest(String email, String password, String username){
        this.email = email;
        this.password = password;
        this.username = username;
    }
}
