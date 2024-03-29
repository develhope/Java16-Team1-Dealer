package com.develhope.spring.client.clientControllerResponse;

import com.develhope.spring.rent.RentDtoOutput;
import lombok.Data;
import org.springframework.stereotype.Component;

import java.util.List;

@Data
@Component
public class ErrorMessagesClient {

    public String messageDeleteAccountOK() {
        return "Account deleted successfully";
    }

    public String messageUpdateAccountOK() {
        return "Account updated successfully";
    }

    public String noDetailsUpdateAccount() {
        return "Please enter details to update account";
    }

    public String vehicleNotFound() {
        return "Vehicle not found";
    }

    public String vehicleFound() {
        return "Vehicle found";
    }

    public String vehiclesNotFoundByRangePrice() {
        return "Vehicles not found by range price";
    }

    public String vehiclesFound() {
        return "Vehicles found";
    }
    public String vehicleNotOrderable(Long idVehicle) {
        return "Vehicle with id " + idVehicle + " is not orderable";
    }

    public String vehicleNotPurchasable(Long idVehicle) {
        return "Vehicle with id " + idVehicle + " is not purchasable";
    }


    public String sellerNotFound(Long idSeller) {
        return "Seller with id " + idSeller + " does not exist";
    }

    public String orderCreated() {
        return "Order created successfully";
    }

    public String purchaseCreated() {
        return "Purchase created successfully";
    }

    public String vehicleNotExist(Long idVehicle) {
        return "Vehicle with id " + idVehicle + " does not exist";
    }

    public String ordersNotFound() {
        return "Orders not found";
    }

    public String ordersFound() {
        return "Orders found";
    }

    public String orderNotExist(Long idOrder) {
        return "Order with id " + idOrder + " does not exist";
    }

    public String orderStatusCancelledOk(Long idOrder) {
        return"Order with id " + idOrder + " cancelled";
    }

    public String purchasesFound() {
        return "Purchases found";
    }

    public String purchasesNotFound() {
        return "Purchases not found";
    }

    public String noFilterApplied() {
        return "No filter applied, showing all vehicles";
    }

    public String listVehiclesFiltered() {
        return "List of vehicles filtered";
    }


    public String rentCreated() { return "Rent created successfully";
    }

    public String vehicleNotRentable(Long idVehicle) {
        return "Vehicle with id " + idVehicle + " is not rentable";
    }

    public String rentListClientOK(int size) {
        return "Your rents: " + size;
    }

    public String rentListClientEmpty() {
        return "You have no active rents";
    }

    public String messageDeleteRentOK() {
        return "Rent deleted successfully";
    }

    public String rentNotFound(Long id) {
        return "We could not find any rent with id " + id;
    }
}
