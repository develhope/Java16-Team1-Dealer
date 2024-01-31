package com.develhope.spring.controllers;

import com.develhope.spring.entities.user.SellerEntity;
import com.develhope.spring.entities.user.UserEntity;
import com.develhope.spring.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;

@RestController
@RequestMapping("/v1/user")
public class UserController {

    @Autowired
    private UserService userService;

    @PostMapping(path = "/create/new/user")
    public UserEntity userCreation(@RequestBody UserEntity user) {
        return userService.createUser(user);
    }
}
