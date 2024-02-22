package com.develhope.spring.seller.sellerControllerResponse;

import lombok.Data;
import org.springframework.stereotype.Component;

@Data
@Component
public class ErrorMessageSeller {

    public String showFoundVehicle(Long idVehicle) {
        return "Vehicle with id: " + idVehicle + " correctly found. These are vehicle's data: ";
    }

    public String vehicleNotFound(Long idVehicle) {
        return "Vehicle with id: " + idVehicle + " does not exists.";
    }

    public String rentCreation() {
        return "Rent correctly created";
    }

    public String rentNotCreated() {
        return """
                Unable to create a new rent with given data.
                 Please check that:
                 -The selected vehicle is rentable and it's status is not 'ORDERABLE'.
                 -The client ID selected exists""";
    }

    public String showFoundClient(Long idClient) {
        return "Client with id: " + idClient + " correctly found. These are client's data: ";
    }

    public String clientNotFound(Long idClient) {
        return "Client with id: " + idClient + " does not exists.";
    }
}
