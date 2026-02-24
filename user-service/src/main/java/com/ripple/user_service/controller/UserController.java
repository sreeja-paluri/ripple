package com.ripple.user_service.controller;

import com.ripple.user_service.entity.User;
import com.ripple.user_service.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

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
}
