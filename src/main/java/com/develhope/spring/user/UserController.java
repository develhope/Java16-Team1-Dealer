package com.develhope.spring.user;

import com.develhope.spring.loginSignup.IdLogin;
import com.develhope.spring.loginSignup.LoginCredentials;
import com.develhope.spring.user.userControllerResponse.CreateNewAccountResponse;
import com.develhope.spring.user.userControllerResponse.LoginAccountResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
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
    @Operation(summary = "Create a new Account")
    @ApiResponse(responseCode = "600", description = "Email already exist")
    @ApiResponse(responseCode = "601", description = "Client created successfully")
    @ApiResponse(responseCode = "602", description = "Seller created successfully")
    @ApiResponse(responseCode = "603", description = "Admin created successfully")
    @PostMapping(path = "/create/new/user")
    public ResponseEntity<CreateNewAccountResponse> userCreation(
            @RequestBody UserEntity user) {
        return userService.createUser(user);
    }

    @Operation(summary = "Login with your credentials")
    @ApiResponse(responseCode = "200", description = "Login successfully")
    @ApiResponse(responseCode = "605", description = "Wrong email or password")
    @ApiResponse(responseCode = "606", description = "Password cannot be blank")
    @ApiResponse(responseCode = "607", description = "Email cannot be blank")
    @GetMapping(path = "/login")
    public ResponseEntity<LoginAccountResponse> login(
            @RequestBody LoginCredentials loginCredentials) {
        return userService.login(loginCredentials);
    }
}
