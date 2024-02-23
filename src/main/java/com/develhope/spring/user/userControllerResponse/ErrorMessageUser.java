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
}
