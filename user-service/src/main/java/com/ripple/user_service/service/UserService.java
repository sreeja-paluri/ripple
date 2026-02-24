package com.ripple.user_service.service;


import com.ripple.user_service.entity.User;
import com.ripple.user_service.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

public interface UserService {
    public List<User>  getUsers();
}

@Service
 class UserServiceImpl implements UserService{

    @Autowired
    UserRepository repository;
    @Override
     public List<User> getUsers() {
         return repository.findAll();
     }
 }