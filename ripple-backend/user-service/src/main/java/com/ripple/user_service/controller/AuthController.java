package com.ripple.user_service.controller;

import com.ripple.user_service.dto.LoginRequestDTO;
import com.ripple.user_service.dto.TokenResponseDTO;
import com.ripple.user_service.dto.UserRequest;
import com.ripple.user_service.dto.UserResponse;
import com.ripple.user_service.response.ApiResponse;
import com.ripple.user_service.service.AuthService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    private final AuthService authService;

    public AuthController(AuthService authService){
        this.authService = authService;
    }

    @PostMapping("/login")
    public ResponseEntity<ApiResponse<TokenResponseDTO>> login(@RequestBody LoginRequestDTO request){
        TokenResponseDTO token = authService.login(request);
        return ResponseEntity.ok(
                new ApiResponse<>(true,"Login successful", token)
        );
    }
    @PostMapping("/register")
    public ResponseEntity<ApiResponse<UserResponse>> register(@Valid @RequestBody UserRequest request) {
        UserResponse user = authService.register(request);
        return ResponseEntity.ok(
                new ApiResponse<>("SUCCESS", "User registered", user)
        );
    }
}
