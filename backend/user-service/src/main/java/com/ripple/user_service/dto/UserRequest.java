package com.ripple.user_service.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserRequest {

    @NotBlank
    private String username;

    @Email
    @NotBlank
    private String email;

    @Size(min = 6)
    private String password;

    public UserRequest(){};

    public UserRequest(String email, String password, String username){
        this.email = email;
        this.password = password;
        this.username = username;
    }
}