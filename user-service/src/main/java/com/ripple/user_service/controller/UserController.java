package com.ripple.user_service.controller;

import com.ripple.user_service.dto.UserRequest;
import com.ripple.user_service.dto.UserResponse;
import com.ripple.user_service.entity.User;
import com.ripple.user_service.response.ApiResponse;
import com.ripple.user_service.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class UserController {

    private final UserService service;

    public UserController(UserService service){
        this.service = service;
    }

    @GetMapping("/api/users")
    public List<User> getUsers(){
        return service.getUsers();
    }

    @PostMapping("/api/users")
    public ResponseEntity<ApiResponse<UserResponse>> createUsers(@RequestBody UserRequest request){
        UserResponse user = service.createUser(request);
        ApiResponse<UserResponse> response = ApiResponse.success("User created successfully", user);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @GetMapping("/api/users/{id}")
    public ResponseEntity<ApiResponse<UserResponse>> getUserById(@PathVariable Long id){
        UserResponse user = service.getUserById(id);
        return ResponseEntity.ok(ApiResponse.success("User Fetched", user));
    }

    @PutMapping("/api/users/{id}")
    public ResponseEntity<ApiResponse<UserResponse>> updateUser( @RequestBody UserRequest request, @PathVariable Long id){
        UserResponse user = service.updateUser(request, id);
        return ResponseEntity.ok(ApiResponse.success("Updated User", user));

    }

    @DeleteMapping("/api/users/{id}")
    public ResponseEntity<ApiResponse<Void>> deleteUser(@PathVariable Long id){
        service.deleteUser(id);
        return ResponseEntity.ok(ApiResponse.success("User Deleted Successfully", null));
    }


}
