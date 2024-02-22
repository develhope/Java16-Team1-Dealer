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

    public String showFoundClient(Long idClient) {
        return "Client with id: " + idClient + " correctly found. These are client's data: ";
    }

    public String clientNotFound(Long idClient) {
        return "Client with id: " + idClient + " does not exists.";
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

    public String rentCorrectlyUpdated(Long idRent) {
        return "The rent with id " + idRent + " has been correctly updated.";
    }

    public String rentNotUpdated(Long idRent) {
        return "The rent with id " + idRent + " can't be updated.\n" +
                "Please check whether the selected ID correctly matches an existing rent.";

    }

    public String rentCorrectlyDeleted(Long idRent) {
        return "The rent with id " + idRent + " has been deleted";
    }

    public String rentNotDeleted(Long idRent) {
        return "The rent with id " + idRent + " can't be deleted.\n" +
                "Please make sure the selected ID matches an existing rent.";
    }

    public String getAllVehiclesOk() {
        return "This is the list of the available vehicles: ";
    }

    public String noVehiclesAvailable() {
        return "There are not available vehicles";
    }

    public String getAllVehiclesRfd() {
        return "This is the list of the vehicles ready for delivery: ";
    }
}
