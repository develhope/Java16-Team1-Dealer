package com.develhope.spring.admin.adminControllerResponse;

import com.develhope.spring.vehicle.VehicleSalesInfoDto;
import lombok.Data;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

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

    public String deleteSellerAdminOK(Long idSeller){
        return "Seller with id " + idSeller + " deleted";
    }

    public String sellerNotExist(Long idSeller){
        return "Seller with id " + idSeller + " does not exist";
    }

    public String deleteClientAdminOK(Long idClient){
        return "Client with id " + idClient + " deleted";
    }

    public String clientNotExist(Long idClient){
        return "Client with id " + idClient + " does not exist";
    }

    public String invalidDateInput (){ return "You did not provide two valid dates for the search.\n" +
            "Please make sure that your input matches the following format: yyyy-MM-ddTHH:mm:ss\n" +
            "Example: 2024-02-20T14:30:00";
    }


    public String validDateInputEarningsInPeriodRange (LocalDateTime firstDate, LocalDateTime secondDate, Integer totalEarnings){
        return "The total earnings between " + firstDate.toLocalDate() + " and " + secondDate.toLocalDate() + " amount to " + totalEarnings;
    }

    public String validDateInputMostSoldCarInPeriodRange (LocalDateTime firstDate, LocalDateTime secondDate, VehicleSalesInfoDto vehicle){
        return "The most sold car between " + firstDate.toLocalDate() + " and " + secondDate.toLocalDate() + " was " + vehicle;
    }

    public String validDateInputMostExpensiveCarSoldCarInPeriodRange (LocalDateTime firstDate, LocalDateTime secondDate, VehicleSalesInfoDto vehicle){
        return "The most expensive car sold between " + firstDate.toLocalDate() + " and " + secondDate.toLocalDate() + " was " + vehicle;
    }

}
