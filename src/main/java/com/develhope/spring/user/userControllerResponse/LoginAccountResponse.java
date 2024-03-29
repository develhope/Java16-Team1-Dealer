package com.develhope.spring.user.userControllerResponse;

import com.develhope.spring.loginSignup.IdLogin;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Schema(name = "LoginAccountResponse", description = "Login account - response")
public class LoginAccountResponse {
    @Schema(description = "Message", example = "Login successfully", required = true)
    private String message;
}
