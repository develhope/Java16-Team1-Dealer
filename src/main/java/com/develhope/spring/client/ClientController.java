package com.develhope.spring.client;

import com.develhope.spring.client.clientControllerResponse.*;
import com.develhope.spring.order.dto.OrderClientDTO;
import com.develhope.spring.order.dto.PurchaseClientDTO;
import com.develhope.spring.rent.RentDtoInput;
import com.develhope.spring.user.UserEntity;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;

@RestController
@RequestMapping("/v1/client")
@Tag(name = "Client Controller", description = "Client Controller API")
public class ClientController {
    @Autowired
    private ClientService clientService;

    @Operation(summary = "Create a new Order")
    @ApiResponse(responseCode = "201", description = "Order created")
    @ApiResponse(responseCode = "600", description = "Vehicle does not exist")
    @ApiResponse(responseCode = "601", description = "Seller does not exist")
    @ApiResponse(responseCode = "602", description = "Vehicle is not orderable")
    @PostMapping("/create/order")
    @ResponseBody
    public ResponseEntity<OrderResponse> newOrder(
            @AuthenticationPrincipal UserEntity user,
            @RequestBody(required = true) OrderClientDTO orderClientDTO) {
        return clientService.createOrder(user, orderClientDTO);
    }

    @Operation(summary = "Get all Orders")
    @ApiResponse(responseCode = "200", description = "Orders found")
    @ApiResponse(responseCode = "404", description = "Orders not found")
    @GetMapping("/show/order/list")
    @ResponseBody
    public ResponseEntity<ListOrderResponse> orderEntityList(@AuthenticationPrincipal UserEntity user) {
        System.out.println(user);
        return clientService.orderEntityList(user);
    }

    @Operation(summary = "Update Order Status To Cancelled")
    @ApiResponse(responseCode = "200", description = "Order status cancelled")
    @ApiResponse(responseCode = "404", description = "Order not found")
    @PutMapping("/update/status/order/cancelled/{idOrder}")
    @ResponseBody
    public ResponseEntity<StatusCancelledResponse> updateStatusCancelledId(
            @Parameter(description = "Order ID", example = "1", required = true, name = "idOrder") @PathVariable(name = "idOrder") Long id) {
        return clientService.updateStatusCancelled(id);
    }

    @Operation(summary = "Create a new Purchase")
    @ApiResponse(responseCode = "201", description = "Purchase created")
    @ApiResponse(responseCode = "600", description = "Vehicle does not exist")
    @ApiResponse(responseCode = "601", description = "Seller does not exist")
    @ApiResponse(responseCode = "602", description = "Vehicle is not orderable")
    @PostMapping("/create/purchase")
    @ResponseBody
    public ResponseEntity<PurchaseResponse> createPurchase(
            @AuthenticationPrincipal UserEntity user,
            @RequestBody(required = true) PurchaseClientDTO purchaseClientDTO) {
        return clientService.createPurchase(user, purchaseClientDTO);
    }

    @Operation(summary = "Get all Purchases")
    @ApiResponse(responseCode = "200", description = "Purchases found")
    @ApiResponse(responseCode = "404", description = "Purchases not found")
    @GetMapping("/show/purchase/list")
    @ResponseBody
    public ResponseEntity<ListPurchaseResponse> showPurchases(@AuthenticationPrincipal UserEntity user) {
        return clientService.purchaseList(user);
    }


    @Operation(summary = "Create a new rent")
    @ApiResponse(responseCode = "201", description = "Rent created")
    @ApiResponse(responseCode = "600", description = "The vehicle you would like to rent does not exist")
    @ApiResponse(responseCode = "601", description = "The seller you would like to rent your car from does not exist")
    @ApiResponse(responseCode = "602", description = "The vehicle you would like to rent is not rentable")
    @PostMapping("/create/rent")
    public @ResponseBody ResponseEntity<NewRentResponse> newRent(
            @AuthenticationPrincipal UserEntity user,
            @Parameter(description = "A rent DTO", required = true, name = "rentDtoInput") @RequestBody RentDtoInput rentDtoInput) {
        return clientService.newRent(user, rentDtoInput);
    }

    @Operation(summary = "Show a complete list of the client's rents")
    @ApiResponse(responseCode = "200", description = "Show client's rents.")
    @ApiResponse(responseCode = "404", description = "No active rents were found.")
    @GetMapping("/show/rent/list")
    public @ResponseBody ResponseEntity<ShowRentListClientResponse> showRents(@AuthenticationPrincipal UserEntity user) {
        return clientService.showRents(user);
    }

    @Operation(summary = "Delete a rent")
    @ApiResponse(responseCode = "200", description = "Rent deleted")
    @ApiResponse(responseCode = "404", description = "No rent matching the id was found")
    @DeleteMapping("/delete/rent/{id}")
    public ResponseEntity<RentDeletionClientResponse> deleteRent(
            @AuthenticationPrincipal UserEntity user,
            @PathVariable Long id) {
        return clientService.deleteRent(user, id);
    }

    @Operation(summary = "Delete My Account")
    @ApiResponse(responseCode = "200", description = "Account deleted")
    @DeleteMapping("/delete/myaccount")
    public ResponseEntity<String> deleteClient(@AuthenticationPrincipal UserEntity user) {
        return clientService.deleteAccount(user);
    }

    @Operation(summary = "Update My Account")
    @ApiResponse(responseCode = "607", description = "Account updated")
    @ApiResponse(responseCode = "406", description = "Please enter details to update account")
    @PatchMapping("/upgrade/myaccount")
    @ResponseBody
    public ResponseEntity<UpdateAccountResponse> updateClient(
            @AuthenticationPrincipal UserEntity user,
            @RequestBody ClientEntity updClient) {
        return clientService.updateAccount(user, updClient);
    }

    @Operation(summary = "Show Vehicle by ID")
    @ApiResponse(responseCode = "302", description = "Vehicle found")
    @ApiResponse(responseCode = "404", description = "Vehicle not found")
    @GetMapping("/show/vehicle/{idVehicle}")
    @ResponseBody
    public ResponseEntity<ShowVehicleIDResponse> showVehicleID(
            @Parameter(description = "Vehicle ID", example = "1", required = true, name = "idVehicle") @PathVariable(name = "idVehicle") Long idVehicle) {
        return clientService.showVehicleID(idVehicle);
    }

    @Operation(summary = "Show Vehicle by Filter")
    @ApiResponse(responseCode = "302", description = "Vehicles found")
    @ApiResponse(responseCode = "610", description = "No filter applied, showing all vehicles")
    @GetMapping("/show/list/vehicle/by/filter")
    @ResponseBody
    public ResponseEntity<ListVehicleFilterResponse> showVehicle(
            @Parameter(description = "Vehicle Brand ", example = "FIAT", required = false, name = "brand") @RequestParam(name = "brand", required = false) String brand,
            @Parameter(description = "Vehicle Model", example = "PANDA", required = false, name = "model") @RequestParam(name = "model", required = false) String model,
            @Parameter(description = "Vehicle Color", example = "ORO", required = false, name = "color") @RequestParam(name = "color", required = false) String color) {
        return clientService.showAllVehiclesFiltered(color, brand, model);
    }

    @Operation(summary = "Show Vehicle by Range Price")
    @ApiResponse(responseCode = "302", description = "Vehicles found")
    @ApiResponse(responseCode = "611", description = "Vehicles not found by range price")
    @GetMapping("/show/list/vehicle/by/rangeprice")
    @ResponseBody
    public ResponseEntity<ListVehicleFilterResponse> findVehicleByRangePrice(
            @Parameter(description = "Minimum price of a vehicle", example = "100", required = true, name = "minPrice") @RequestParam(name = "minPrice", required = true) BigDecimal minPrice,
            @Parameter(description = "Maximum price of a vehicle", example = "1500", required = true, name = "maxPrice") @RequestParam(name = "maxPrice", required = true) BigDecimal maxPrice) {
        return clientService.filterFindVehicleByRangePrice(minPrice, maxPrice);
    }

}

