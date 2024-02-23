package com.develhope.spring.loginSignup;


import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Schema(name = "Login Credentials", description = "Login Credentials")
public class LoginCredentials {
    @Schema(description = "Email", example = "mario.rossi@gmail.com", required = true)
    private String email;
    @Schema(description = "Password", example = "password", required = true)
    private String psw;
}
