package com.develhope.spring.user;

import com.develhope.spring.loginSignup.AuthenticationServiceImpl;
import com.develhope.spring.loginSignup.LoginCredentials;
import com.develhope.spring.user.userControllerResponse.CreateNewAccountResponse;
import com.develhope.spring.user.userControllerResponse.LoginAccountResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/v1/user")
@Tag(name = "User Controller", description = "User Controller API")
public class UserController {
    @Autowired
    private AuthenticationServiceImpl authenticationService;
    @Operation(summary = "Create a new Account")
    @ApiResponse(responseCode = "600", description = "Email already exist")
    @ApiResponse(responseCode = "601", description = "Client created successfully")
    @ApiResponse(responseCode = "602", description = "Seller created successfully")
    @ApiResponse(responseCode = "603", description = "Admin created successfully")
    @PostMapping(path = "/create/new/user")
    public ResponseEntity<CreateNewAccountResponse> userCreation(
            @RequestBody UserEntity user) {
        return authenticationService.signup(user);
    }

//    @Operation(summary = "Login with your credentials")
//    @ApiResponse(responseCode = "200", description = "Login successfully")
//    @ApiResponse(responseCode = "605", description = "Wrong email or password")
//    @ApiResponse(responseCode = "606", description = "Password cannot be blank")
//    @ApiResponse(responseCode = "607", description = "Email cannot be blank")
//    @PostMapping(path = "/login")
//    public ResponseEntity<LoginAccountResponse> login(
//            @RequestBody LoginCredentials loginCredentials) {
//        return authenticationService.signin(loginCredentials);
//    }

    @Operation(summary = "Login with your credentials")
    @ApiResponse(responseCode = "200", description = "Login successfully")
    @ApiResponse(responseCode = "605", description = "Wrong email or password")
    @ApiResponse(responseCode = "606", description = "Password cannot be blank")
    @ApiResponse(responseCode = "607", description = "Email cannot be blank")
    @PostMapping(path = "/login/token")
    public ResponseEntity<LoginAccountResponse> loginToken(
            @RequestBody LoginCredentials loginCredentials) {
        return authenticationService.signin(loginCredentials);
    }

}
