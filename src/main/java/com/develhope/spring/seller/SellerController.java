package com.develhope.spring.seller;


import com.develhope.spring.order.OrderEntity;
import com.develhope.spring.order.OrderState;
import com.develhope.spring.rent.RentDtoInput;
import com.develhope.spring.rent.RentEntity;
import com.develhope.spring.rent.ToUpdateRentDtoInput;
import com.develhope.spring.seller.sellerControllerResponse.*;
import com.develhope.spring.user.UserEntity;
import com.develhope.spring.vehicle.VehicleEntity;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping(path = "/v1/seller")
@Tag(name = "Seller Controller", description = "Seller Controller API")
public class SellerController {

    @Autowired
    private SellerService sellerService;


    @Operation(summary = "Find vehicle by ID")
    @ApiResponse(responseCode = "200", description = "Vehicle correctly found")
    @ApiResponse(responseCode = "404", description = "Vehicle not found")
    @GetMapping(path = "/show/vehicle/id/{id}")
    public ResponseEntity<GetVehicleByIdFromSellerResponse> getVehicleById(
            @Parameter(description = "Vehicle ID", example = "1", required = true, name = "id") @PathVariable(name = "id") Long id
    ) {
        return sellerService.getVehicleById(id);
    }

    @PostMapping(path = "/create/order")
    @ResponseBody
    public OrderEntity newOrder(
            @AuthenticationPrincipal UserEntity user,
            @RequestBody(required = true) OrderEntity order,
            @RequestParam(name = "id_client", required = true) Long idClient,
            @RequestParam(name = "id_vehicle", required = true) Long idVehicle) {
        return sellerService.newOrder(user, order, idClient, idVehicle);
    }

    @PutMapping(path = "/update/status/order/cancelled/{idOrder}")
    @ResponseBody
    public OrderEntity deleteOrder(
            @PathVariable(name = "idOrder") Long id) {
        return sellerService.updateStatusCanceled(id);
    }

    @PatchMapping(path = "/update/order/{idOrder}")
    public OrderEntity editOrder(
            @PathVariable(name = "idOrder") Long idOrder,
            @RequestBody OrderEntity order) {

        return sellerService.updateOrder(order, idOrder);
    }

    @GetMapping(path = "/show/order/{id}")
    public OrderEntity checkOrder(@PathVariable(name = "id") Long id) {
        return sellerService.checkOrder(id);
    }

    @GetMapping(path = "/show/order/list/by/status")
    public List<OrderEntity> checkAllOrdersByStatus(@RequestParam(name = "status") OrderState status) {
        return sellerService.checkAllOrdersByStatus(status);
    }

    @Operation(summary = "Create new rent")
    @ApiResponse(responseCode = "200", description = "Rent correctly created")
    @ApiResponse(responseCode = "404", description = "Rent can't be updated")
    @PostMapping(path = "/create/rent")
    public @ResponseBody ResponseEntity<RentCreationFromSellerResponse> createNewRent(
            @AuthenticationPrincipal UserEntity user,
            @RequestBody(required = true) RentDtoInput rent
    ) {
        return sellerService.createRent(user, rent);
    }

    @Operation(summary = "Update rent by ID")
    @ApiResponse(responseCode = "200", description = "Rent correctly updated")
    @ApiResponse(responseCode = "404", description = "Rent can't be updated")
    @PatchMapping(path = "/update/rent/{rentId}")
    public ResponseEntity<RentUpdateFromSellerResponse> updateRent(
            @Parameter(description = "Rent ID", example = "1", required = true) @PathVariable(name = "rentId") long id,
            @RequestBody ToUpdateRentDtoInput toUpdateRentDtoInput
    ) {
        return sellerService.updateRent(toUpdateRentDtoInput, id);
    }

    @Operation(summary = "Delete rent by ID")
    @ApiResponse(responseCode = "200", description = "Rent correctly deleted")
    @ApiResponse(responseCode = "404", description = "Rent can't be deleted")
    @DeleteMapping(path = "/delete/rent/{rentId}")

    public ResponseEntity<RentDeletionByIdFromSellerResponse> deleteRent(
            @Parameter(description = "Rent ID", example = "1", required = true) @PathVariable(name = "rentId") long id) {
        return sellerService.deleteRent(id);
    }

    @Operation(summary = "Get all vehicles")
    @ApiResponse(responseCode = "200", description = "Vehicles found")
    @ApiResponse(responseCode = "404", description = "No vehicles available")
    @GetMapping(path = "/show/all/vehicles")
    public ResponseEntity<GetAllVehiclesFromSellerResponse> getAllVehicles() {
        return sellerService.getAllVehicles();
    }

    @Operation(summary = "Get all vehicles ready for delivery")
    @ApiResponse(responseCode = "200", description = "Vehicles RFD found")
    @ApiResponse(responseCode = "404", description = "Vehicles RFD not found")
    @GetMapping(path = "/show/all/vehicles/rfd")
    public ResponseEntity<GetVehiclesRfdFromSellerResponse> getAllVehiclesRfd() {
        return sellerService.getAllVehiclesRfd();
    }

    @Operation(summary = "Get all rentable vehicles")
    @ApiResponse(responseCode = "200", description = "Vehicles rentable found")
    @ApiResponse(responseCode = "404", description = "Vehicles rentable not found")
    @GetMapping(path = "/show/all/rentable/vehicles")
    public ResponseEntity<GetAllRentableVehiclesFromSellerResponse> getAllRentableVehicles() {
        return sellerService.getAllRentableVehicles();
    }

}
