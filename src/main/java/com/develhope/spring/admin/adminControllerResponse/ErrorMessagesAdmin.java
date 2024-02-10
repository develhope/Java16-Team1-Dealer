package com.develhope.spring.admin.adminControllerResponse;

import lombok.Data;
import org.springframework.stereotype.Component;

@Data
@Component
public class ErrorMessagesAdmin {

    public String updateClientbyAdminNotFoundClient(Long idClient) {
        return "Client with id " + idClient + " not found!";
    }

    public String updateClientbyAdminNotClient(Long idClient) {
        return "Client with id " + idClient + " is not a client";
    }

    public String updateClientbyAdminOK(Long idClient) {
        return "Client with id " + idClient + " updated";
    }

    public String updateClientbyAdminEmailExist(String email) {
        return "Client with email " + email + " already exist";
    }

}
