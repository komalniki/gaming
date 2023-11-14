package com.game.service.impl;

import com.game.dto.request.UserRequest;
import com.game.dto.response.UserResponse;
import com.game.exception.ApplicationException;
import com.game.models.User;
import com.game.repository.UsersRepository;
import com.game.service.UserService;
import com.game.utils.UserUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Slf4j
@Service
public class UserServiceImpl implements UserService {

    private final UsersRepository usersRepository;

    @Autowired
    public UserServiceImpl(UsersRepository usersRepository) {
        this.usersRepository = usersRepository;
    }

    @Override
    public UserResponse createUser(UserRequest userRequest) {
        log.info("Request to create User with : {}", userRequest);
        if (this.checkIfUserExistsWithProvidedName(userRequest.getName())) {
            throw new ApplicationException("User Already Exists with Provided Name");
        }
        return UserUtils.populateUserResponse(this.usersRepository.save(UserUtils.populateUserEntityForCreation(userRequest)));
    }

    @Override
    public UserResponse updateUser(Integer userId, UserRequest userRequest) {
        log.info("Request to Update User with Id: {} and Meta as : {}", userId, userRequest);
        User user = this.fetchUserById(userId);
        if (this.checkIfUserExistsWithProvidedName(userRequest.getName())) {
            throw new ApplicationException("User Already Exists with Provided Name");
        }
        UserUtils.populateUserEntityForUpdation(user, userRequest);
        return UserUtils.populateUserResponse(this.usersRepository.save(user));
    }

    @Override
    public void deleteUser(Integer userId) {
        log.info("Request received to Delete User with Id: {}", userId);
        User user = this.fetchUserById(userId);
        UserUtils.populateUserEntityForDeletion(user);
        this.usersRepository.save(user);
    }

    @Override
    public UserResponse getUserById(Integer userId) {
        log.info("Request received to Fetch User Details with Id: {}", userId);
        return UserUtils.populateUserResponse(this.fetchUserById(userId));
    }

    @Override
    public List<UserResponse> fetchAllUsers() {
        return this.usersRepository.findAll().stream()
                .filter(Objects::nonNull)
                .map(UserUtils::populateUserResponse)
                .collect(Collectors.toList());
    }

    @Override
    public List<UserResponse> fetchAllActiveUsers() {
        return this.usersRepository.findUsersByActiveTrue().stream()
                .filter(Objects::nonNull)
                .map(UserUtils::populateUserResponse)
                .collect(Collectors.toList());
    }

    @Override
    public User fetchUserById(Integer userId) {
        return this.usersRepository.findById(userId)
                .orElseThrow(() -> new ApplicationException("No User Exists with given Id : " + userId));
    }

    private boolean checkIfUserExistsWithProvidedName(String userName) {
        return this.usersRepository.findUserByUserName(userName).isPresent();
    }
}