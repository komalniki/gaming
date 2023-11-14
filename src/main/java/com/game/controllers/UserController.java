package com.game.controllers;

import com.game.dto.Response;
import com.game.dto.request.UserRequest;
import com.game.dto.response.UserResponse;
import com.game.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@Slf4j
@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserService userService;

    @PostMapping(value = "/create")
    public ResponseEntity<Response<UserResponse>> createUser(@Valid @RequestBody UserRequest userRequest) {
        return new ResponseEntity<>(Response.successResponse(this.userService.createUser(userRequest)), HttpStatus.CREATED);
    }

    @PostMapping(value = "/update")
    public ResponseEntity<Response<UserResponse>> updateUser(@Valid @RequestParam(value = "userId") Integer userId, @RequestBody UserRequest userRequest) {
        return new ResponseEntity<>(Response.successResponse(this.userService.updateUser(userId, userRequest)), HttpStatus.CREATED);
    }

    @DeleteMapping(value = "/delete")
    public ResponseEntity<Response<Object>> deleteUser(@RequestParam(value = "userId") Integer userId) {
        this.userService.deleteUser(userId);
        return new ResponseEntity<>(Response.successResponse(), HttpStatus.ACCEPTED);
    }

    @GetMapping(value = "/getUserById")
    public ResponseEntity<Response<UserResponse>> getUserDetailsById(@RequestParam(value = "userId") Integer userId) {
        return new ResponseEntity<>(Response.successResponse(this.userService.getUserById(userId)), HttpStatus.FOUND);
    }

    @GetMapping(value = "/getAllUsers")
    public ResponseEntity<Response<List<UserResponse>>> getAllUserDetails() {
        log.info("Fetching all Users");
        return new ResponseEntity<>(Response.successResponse(this.userService.fetchAllUsers()), HttpStatus.FOUND);
    }

    @GetMapping(value = "/getAllActiveUsers")
    public ResponseEntity<Response<List<UserResponse>>> getAllActiveUserDetails() {
        log.info("Fetching All Active users");
        return new ResponseEntity<>(Response.successResponse(this.userService.fetchAllActiveUsers()), HttpStatus.FOUND);
    }
}