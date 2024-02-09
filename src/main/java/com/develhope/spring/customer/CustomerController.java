package com.develhope.spring.customer;

import com.develhope.spring.customer.controllerResponses.*;
import com.develhope.spring.rent.DTOs.rentCreationDTO;
import com.develhope.spring.order.DTOs.OrderClientDTO;
import com.develhope.spring.order.DTOs.PurchaseClientDTO;
import com.develhope.spring.rent.RentEntity;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.List;

@RestController
@RequestMapping("/v1/client")
@Tag(name = "Client Controller", description = "Client Controller API")
public class CustomerController {
    @Autowired
    private CustomerService customerService;

    @Operation(summary = "Create a new Order")
    @ApiResponse(responseCode = "201", description = "Order created")
    @ApiResponse(responseCode = "600", description = "Vehicle does not exist")
    @ApiResponse(responseCode = "601", description = "Seller does not exist")
    @ApiResponse(responseCode = "602", description = "Vehicle is not orderable")
    @PostMapping("/create/order")
    @ResponseBody
    public ResponseEntity<OrderResponse> newOrder(
            @RequestBody(required = true) OrderClientDTO orderClientDTO) {
        return customerService.createOrder(orderClientDTO);
    }

    @Operation(summary = "Get all Orders")
    @ApiResponse(responseCode = "200", description = "Orders found")
    @ApiResponse(responseCode = "404", description = "Orders not found")
    @GetMapping("/show/order/list")
    @ResponseBody
    public ResponseEntity<ListOrderResponse> orderEntityList() {
        return customerService.orderEntityList();
    }

    @Operation(summary = "Update Order Status To Cancelled")
    @ApiResponse(responseCode = "200", description = "Order status cancelled")
    @ApiResponse(responseCode = "404", description = "Order not found")
    @PutMapping("/update/status/order/cancelled/{idOrder}")
    @ResponseBody
    public ResponseEntity<StatusCancelledResponse> updateStatusCancelledId(
            @Parameter(description = "Order ID", example = "1", required = true, name = "idOrder") @PathVariable(name = "idOrder") Long id) {
        return customerService.updateStatusCancelled(id);
    }

    @Operation(summary = "Create a new Purchase")
    @ApiResponse(responseCode = "201", description = "Purchase created")
    @ApiResponse(responseCode = "600", description = "Vehicle does not exist")
    @ApiResponse(responseCode = "601", description = "Seller does not exist")
    @ApiResponse(responseCode = "602", description = "Vehicle is not orderable")
    @PostMapping("/create/purchase")
    @ResponseBody
    public ResponseEntity<PurchaseResponse> createPurchase(
            @RequestBody(required = true) PurchaseClientDTO purchaseClientDTO) {
        return customerService.createPurchase(purchaseClientDTO);
    }

    @Operation(summary = "Get all Purchases")
    @ApiResponse(responseCode = "200", description = "Purchases found")
    @ApiResponse(responseCode = "404", description = "Purchases not found")
    @GetMapping("/show/purchase/list")
    @ResponseBody
    public ResponseEntity<ListPurchaseResponse> showPurchases() {
        return customerService.purchaseList();
    }


    @PostMapping("/create/rent")
    @ResponseBody
    public RentEntity newRent(@RequestBody rentCreationDTO rentCreationDTO) {
        return customerService.newRent(rentCreationDTO);
    }

    @GetMapping("/show/rent/list")
    public @ResponseBody List<RentEntity> showRents() {
        return customerService.showRents();
    }

    @DeleteMapping("/delete/rent/{id}")
    public void deleteRent(@PathVariable Long id) {
        customerService.deleteRent(id);
    }

    @Operation(summary = "Delete My Account")
    @ApiResponse(responseCode = "200", description = "Account deleted")
    @DeleteMapping("/delete/myaccount")
    public ResponseEntity<String> deleteClient() {
        return customerService.deleteAccount();
    }

    @Operation(summary = "Update My Account")
    @ApiResponse(responseCode = "607", description = "Account updated")
    @ApiResponse(responseCode = "406", description = "Please enter details to update account")
    @PatchMapping("/upgrade/myaccount")
    @ResponseBody
    public ResponseEntity<UpdateAccountResponse> updateClient(
            @RequestBody CustomerEntity updClient) {
        return customerService.updateAccount(updClient);
    }

    @Operation(summary = "Show Vehicle by ID")
    @ApiResponse(responseCode = "302", description = "Vehicle found")
    @ApiResponse(responseCode = "404", description = "Vehicle not found")
    @GetMapping("/show/vehicle/{idVehicle}")
    @ResponseBody
    public ResponseEntity<ShowVehicleIDResponse> showVehicleID(
            @Parameter(description = "Vehicle ID", example = "1", required = true, name = "idVehicle") @PathVariable(name = "idVehicle") Long idVehicle) {
        return customerService.showVehicleID(idVehicle);
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
        return customerService.showAllVehiclesFilteted(color, brand, model);
    }

    @Operation(summary = "Show Vehicle by Range Price")
    @ApiResponse(responseCode = "302", description = "Vehicles found")
    @ApiResponse(responseCode = "611", description = "Vehicles not found by range price")
    @GetMapping("/show/list/vehicle/by/rangeprice")
    @ResponseBody
    public ResponseEntity<ListVehicleFilterResponse> findVehicleByRangePrice(
            @Parameter(description = "Minimum price of a vehicle", example = "100", required = true, name = "minPrice") @RequestParam(name = "minPrice", required = true) BigDecimal minPrice,
            @Parameter(description = "Maximum price of a vehicle", example = "1500", required = true, name = "maxPrice") @RequestParam(name = "maxPrice", required = true) BigDecimal maxPrice) {
        return customerService.filterFindVehicleByRangePrice(minPrice, maxPrice);
    }

}

