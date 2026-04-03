package com.ripple.user_service.service;


import com.ripple.user_service.dto.UserRequest;
import com.ripple.user_service.dto.UserResponse;
import com.ripple.user_service.entity.User;
import com.ripple.user_service.exception.UserNotFoundException;
import com.ripple.user_service.mapper.UserMapper;
import com.ripple.user_service.repository.UserRepository;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.stream.Collectors;


public interface UserService {

    Page<UserResponse> getAllUsers(Pageable pageable);

    UserResponse createUser(UserRequest request);

   UserResponse getUserById(Long id);

   UserResponse updateUser(UserRequest request, Long id);

   void deleteUser(Long id);

   List<UserResponse> getUsersByIds(List<Long> id);
}

@Service
 class UserServiceImpl implements UserService{

    @Autowired
    UserRepository repository;
    private static final Logger log = LoggerFactory.getLogger(UserServiceImpl.class);

    @Override
    public Page<UserResponse> getAllUsers(Pageable pageable) {
        return repository.findAll(pageable)
                .map(UserMapper::toResponse);
    }
     @Override
    public UserResponse createUser(UserRequest request){
        User user = UserMapper.toEntity(request); // convert json to entity
       User savedUser = repository.save(user); // save
         log.info("User created with email={}", user.getEmail());
       return UserMapper.toResponse(savedUser);// use mapper and convert to response DTO
     }

    @Override
    public UserResponse getUserById(Long id)  {
        User user = repository.findById(id).orElseThrow(() -> new UserNotFoundException("User not found"));
        return UserMapper.toResponse(user);
    }

    @Override
    public UserResponse updateUser(UserRequest request, Long id){
        User user = repository.findById(id).orElseThrow(() -> new UserNotFoundException("User not found" +id));
        user.setUsername(request.getUsername());
        user.setPassword(request.getPassword());
        user.setEmail(request.getEmail());

        User updatedUser = repository.save(user);
        return UserMapper.toResponse(updatedUser);
    }

    @Override
    public void deleteUser(Long id){
        User user = repository.findById(id).orElseThrow(() -> new UserNotFoundException("User not found" +id));
        repository.delete(user);
    }

    @Override
    public List<UserResponse> getUsersByIds(List<Long> ids){
        List<User> userList = repository.findByIdIn(ids);
        return  userList.stream()
                .map(User -> UserMapper.toResponse(User))
                .collect(Collectors.toList());
    }

 }