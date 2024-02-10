package com.develhope.spring.client.clientControllerResponse;

import com.develhope.spring.client.ClientEntity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UpdateAccountResponse {

    private String message;
    private ClientEntity clientEntity;

}
