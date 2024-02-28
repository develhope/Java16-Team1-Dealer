package com.develhope.spring.loginSignup;

import com.develhope.spring.user.UserEntity;
import com.develhope.spring.user.userControllerResponse.CreateNewAccountResponse;
import com.develhope.spring.user.userControllerResponse.LoginAccountResponse;
import org.springframework.http.ResponseEntity;

public interface AuthenticationService {
    ResponseEntity<CreateNewAccountResponse> signup(UserEntity request);

    ResponseEntity<LoginAccountResponse> signin(LoginCredentials loginCredentials);
}
