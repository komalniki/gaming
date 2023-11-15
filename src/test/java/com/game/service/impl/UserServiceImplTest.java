package com.game.service.impl;

import com.game.dto.request.UserRequest;
import com.game.exception.ApplicationException;
import com.game.models.User;
import com.game.repository.UsersRepository;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Optional;

@RunWith(MockitoJUnitRunner.class)
public class UserServiceImplTest {

    @Mock
    private UsersRepository usersRepository;

    private UserRequest userRequest;

    private UserServiceImpl userService;

    @Before
    public void setUp() {
        userRequest = Mockito.mock(UserRequest.class);
        Mockito.when(userRequest.getName()).thenReturn("testUser");
        userService = new UserServiceImpl(usersRepository);
    }

    @Test(expected = ApplicationException.class)
    public void createUser_user_already_exists() {
        Mockito.when(this.usersRepository.findUserByUserName(userRequest.getName())).thenReturn(Optional.of(Mockito.mock(User.class)));
        this.userService.createUser(userRequest);
    }

    @Test
    public void createUser() {
        Mockito.when(this.usersRepository.findUserByUserName(userRequest.getName())).thenReturn(Optional.empty());
        User user = Mockito.mock(User.class);
        Mockito.when(this.usersRepository.save(Mockito.any())).thenReturn(user);
        Assert.assertNotNull(this.userService.createUser(userRequest));
    }

    @Test(expected = ApplicationException.class)
    public void updateUser_name_already_exists() {
        Mockito.when(usersRepository.findById(Mockito.anyInt())).thenReturn((Optional.of(Mockito.mock(User.class))));
        Mockito.when(this.usersRepository.findUserByUserName(userRequest.getName())).thenReturn(Optional.of(Mockito.mock(User.class)));
        this.userService.updateUser(1001, userRequest);
    }

    @Test
    public void updateUser() {
        Mockito.when(usersRepository.findById(1001)).thenReturn((Optional.of(Mockito.mock(User.class))));
        Mockito.when(this.usersRepository.findUserByUserName(userRequest.getName())).thenReturn(Optional.empty());
        Mockito.when(this.usersRepository.save(Mockito.any())).thenReturn(Mockito.mock(User.class));
        Assert.assertNotNull(this.userService.updateUser(1001, userRequest));
    }

    @Test(expected = ApplicationException.class)
    public void deleteUser_user_not_exists() {
        Mockito.when(usersRepository.findById(1001)).thenReturn(Optional.empty());
        this.userService.deleteUser(1001);
    }

    @Test
    public void deleteUser() {
        Mockito.when(usersRepository.findById(1001)).thenReturn(Optional.of(Mockito.mock(User.class)));
        this.userService.deleteUser(1001);
    }

    @Test(expected = ApplicationException.class)
    public void getUserById_user_not_found() {
        Mockito.when(this.usersRepository.findById(1001)).thenReturn(Optional.empty());
        this.userService.getUserById(1001);
    }

    @Test
    public void getUserById() {
        Mockito.when(this.usersRepository.findById(1001)).thenReturn(Optional.of(Mockito.mock(User.class)));
        Assert.assertNotNull(this.userService.getUserById(1001));
    }

    @Test
    public void fetchAllUsers_no_user_exists() {
        Mockito.when(this.usersRepository.findAll()).thenReturn(new ArrayList<>());
        Assert.assertEquals(0, this.userService.fetchAllUsers().size());
    }

    @Test
    public void fetchAllUsers() {
        Mockito.when(this.usersRepository.findAll()).thenReturn(Arrays.asList(Mockito.mock(User.class)));
        Assert.assertEquals(1, this.userService.fetchAllUsers().size());
    }

    @Test
    public void fetchAllActiveUsers_no_active_users() {
        Mockito.when(this.usersRepository.findUsersByActiveTrue()).thenReturn(new ArrayList<>());
        Assert.assertEquals(0, this.userService.fetchAllActiveUsers().size());
    }

    @Test
    public void fetchAllActiveUsers() {
        Mockito.when(this.usersRepository.findUsersByActiveTrue()).thenReturn(Arrays.asList(Mockito.mock(User.class)));
        Assert.assertEquals(1, this.userService.fetchAllActiveUsers().size());
    }
}