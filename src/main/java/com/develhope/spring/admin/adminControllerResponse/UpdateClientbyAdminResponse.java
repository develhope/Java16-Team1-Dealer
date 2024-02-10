package com.develhope.spring.admin.adminControllerResponse;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import com.develhope.spring.client.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UpdateClientbyAdminResponse {

    private String message;
    private ClientEntity clientEntity;
}
