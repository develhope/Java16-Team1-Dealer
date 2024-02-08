package com.develhope.spring.response.clientControllerResponse;

import com.develhope.spring.entities.user.ClientEntity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UpdateAccountResponse {

    private String messageError;
    private ClientEntity clientEntity;

}
