package com.develhope.spring.user.userControllerResponse;

import com.develhope.spring.user.UserEntity;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Schema(name = "CreateNewAccountResponse", description = "Create new account - response")
public class CreateNewAccountResponse {
    @Schema(description = "Message", example = "Client created successfully", required = true)
    private String message;
    @Schema(description = "User Account", required = true)
    private UserEntity userEntity;
    @Schema(description = "Access Token")
    private String token;

    public CreateNewAccountResponse(String message) {
        this.message = message;
    }
}
