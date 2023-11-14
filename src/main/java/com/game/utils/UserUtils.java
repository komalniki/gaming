package com.game.utils;

import com.game.dto.request.GameRequest;
import com.game.dto.request.UserRequest;
import com.game.dto.response.UserResponse;
import com.game.models.Game;
import com.game.models.ScoreBoard;
import com.game.models.User;

import java.sql.Timestamp;

public class UserUtils {

    private UserUtils() {
    }

    public static User populateUserEntityForCreation(UserRequest userRequest) {
        User user = new User();
        user.setUserName(userRequest.getName());
        user.setCreationDate(new Timestamp(System.currentTimeMillis()));
        user.setLastModifiedDate(new Timestamp(System.currentTimeMillis()));
        user.setActive(Boolean.TRUE);
        return user;
    }

    public static void populateUserEntityForUpdation(User user, UserRequest userRequest) {
        user.setUserName(userRequest.getName());
        user.setLastModifiedDate(new Timestamp(System.currentTimeMillis()));
        user.setActive(Boolean.TRUE);
    }

    public static void populateUserEntityForDeletion(User user) {
        user.setLastModifiedDate(new Timestamp(System.currentTimeMillis()));
        user.setActive(Boolean.FALSE);
    }

    public static UserResponse populateUserResponse(User user) {
        UserResponse response = new UserResponse();
        response.setId(user.getUserId());
        response.setName(user.getUserName());
        response.setActive(user.isActive());
        response.setCreationDate(user.getCreationDate());
        response.setLastModifiedDate(user.getLastModifiedDate());
        return response;
    }
}