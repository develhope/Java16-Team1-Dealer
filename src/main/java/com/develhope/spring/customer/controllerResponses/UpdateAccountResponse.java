package com.develhope.spring.customer.controllerResponses;

import com.develhope.spring.customer.CustomerEntity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UpdateAccountResponse {

    private String message;
    private CustomerEntity customerEntity;

}
