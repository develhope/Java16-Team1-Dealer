package com.develhope.spring.user.userControllerResponse;

import lombok.Data;
import org.springframework.stereotype.Component;

@Data
@Component
public class ErrorMessageUser {

    public String clienCreate() {
        return "Client created successfully";
    }

    public String sellerCreate() {
        return "Seller created successfully";
    }

    public String adminCreate() {
        return "Admin created successfully";
    }

    public String emailExist() {
        return "Email already exist";
    }

    public String emailBlank() {
        return "Email cannot be blank";
    }

    public String pswBlank() {
        return "Password cannot be blank";
    }

    public String okLogin(String email) {
        return "Login successfully with email: " + email ;
    }

    public String errorLogin() {
        return "Wrong email or password";
    }
}
