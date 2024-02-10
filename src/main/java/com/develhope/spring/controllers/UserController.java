package com.develhope.spring.controllers;

import com.develhope.spring.loginSignup.IdLogin;
import com.develhope.spring.loginSignup.LoginCredentials;
import com.develhope.spring.user.UserEntity;
import com.develhope.spring.services.UserService;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/v1/user")
@Tag(name = "User Controller", description = "User Controller API")
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
