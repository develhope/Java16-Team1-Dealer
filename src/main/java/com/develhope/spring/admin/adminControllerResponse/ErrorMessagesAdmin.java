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

    public String listVehiclesAdminOK(int size){
        return "Number of vehicles: " + size;
    }

    public String listVehiclesAdminEmpty(){
        return "No vehicles exist";
    }

    public String createCarAdminOK(){
        return "Car created";
    }

    public String createMotoAdminOK(){
        return "Moto created";
    }

    public String createTruckAdminOK(){
        return "Truck created";
    }

    public String createScooterAdminOK(){
        return "Scooter created";
    }

    public String vehicleNotExist(){
        return "Vehicle does not exist";
    }
    public String vehicleExist(){
        return "Vehicle exists";
    }

    public String updateVehicleAdminOK(){
        return "Vehicle updated";
    }

    public String deleteVehicleAdminOK(){
        return "Vehicle deleted";
    }

}
