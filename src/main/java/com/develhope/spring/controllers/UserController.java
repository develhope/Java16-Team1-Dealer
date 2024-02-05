package com.develhope.spring.controllers;

import com.develhope.spring.dto.IdLogin;
import com.develhope.spring.dto.LoginCredentials;
import com.develhope.spring.entities.user.UserEntity;
import com.develhope.spring.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/v1/user")
public class UserController {

    @Autowired
    private UserService userService;

    @Autowired
    private IdLogin idLogin;

    @PostMapping(path = "/create/new/user")
    public UserEntity userCreation(@RequestBody UserEntity user) {
        return userService.createUser(user);
    }

    @GetMapping(path = "/login")
    public Optional<IdLogin> login(@RequestBody LoginCredentials loginCredentials) {
        Optional<UserEntity> user = userService.login(loginCredentials);
        idLogin.setId(user.get().getId());
        idLogin.setType(user.get().getType().toString());
        return Optional.ofNullable(idLogin);
    }
}
