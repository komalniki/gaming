package com.game.service;

import com.game.dto.request.UserRequest;
import com.game.dto.response.UserResponse;
import com.game.models.User;

import java.util.List;

public interface UserService {

    UserResponse createUser(UserRequest userRequest);

    UserResponse updateUser(Integer userId, UserRequest userRequest);

    void deleteUser(Integer userId);

    UserResponse getUserById(Integer userId);

    User fetchUserById(Integer userId);

    List<UserResponse> fetchAllUsers();

    List<UserResponse> fetchAllActiveUsers();
}