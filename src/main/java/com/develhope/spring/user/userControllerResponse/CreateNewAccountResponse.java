package com.develhope.spring.user.userControllerResponse;

import com.develhope.spring.user.UserEntity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CreateNewAccountResponse {

    private String message;

    private UserEntity userEntity;
}
