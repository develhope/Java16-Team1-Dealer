package com.develhope.spring.admin;

import com.develhope.spring.admin.adminControllerResponse.*;
import com.develhope.spring.client.ClientEntity;
import com.develhope.spring.order.*;
import com.develhope.spring.rent.*;
import com.develhope.spring.seller.SellerEntity;
import com.develhope.spring.user.UserEntity;
import com.develhope.spring.vehicle.VehicleEntity;
import com.develhope.spring.vehicle.VehicleSalesInfoDto;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.time.LocalDateTime;

@RequestMapping("/v1/admin")
@RestController
@Tag(name = "Admin Controller", description = "Admin Controller API")
public class AdminController {

    @Autowired
    private AdminService adminService;

    @PostMapping("/create/order/client")
    public OrderEntity createOrder(
            @RequestBody(required = true) OrderEntity order,
            @RequestParam(name = "id_seller", required = true) Long idSeller,
            @RequestParam(name = "id_vehicle", required = true) Long idVehicle,
            @RequestParam(name = "id_client", required = true) Long idClient
    ) {
        return adminService.createOrder(order, idSeller, idVehicle, idClient);
    }

    @PutMapping("/update/status/order/client/cancelled/{idOrder}")
    @ResponseBody
    public OrderEntity deleteOrder(
            @PathVariable(name = "idOrder") Long idOrder) {
        return adminService.updateStatusCancelled(idOrder);
    }

    @PatchMapping("/update/order/client/{idOrder}")
    @ResponseBody
    public OrderEntity updateOrder(
            @RequestBody OrderEntity order,
            @PathVariable(name = "idOrder") Long idOrder) {
        return adminService.updateOrder(order, idOrder);
    }

    @PostMapping("/create/client/rent")
    @ResponseBody
    public RentEntity createRent(@RequestBody RentDtoInput rentDtoInput) {
        return adminService.createRent(rentDtoInput);
    }

    @DeleteMapping("/delete/client/rent/{id}")
    public RentEntity deleteRent(@PathVariable Long id) {
        return adminService.deleteRent(id);
    }

    @PatchMapping("/update/client/rent/{id}")
    public RentEntity updateRent(@PathVariable Long id, @RequestBody RentDtoInput rentDtoInput) {
        return adminService.updateRent(id, rentDtoInput);
    }

    @PostMapping("/create/purchase/client")
    @ResponseBody
    public OrderEntity createPurchase(
            @RequestBody(required = true) OrderEntity order,
            @RequestParam(name = "id_seller", required = true) Long idSeller,
            @RequestParam(name = "id_vehicle", required = true) Long idVehicle,
            @RequestParam(name = "id_client", required = true) Long idClient) {

        return adminService.createPurchase(order, idSeller, idVehicle, idClient);
    }

    @PutMapping("/update/status/purchase/client/cancelled/{idOrder}")
    @ResponseBody
    public OrderEntity updateStatusCancelledIdPurchase(
            @PathVariable(name = "idOrder") Long id
    ) {
        return adminService.updateStatusCancelledPurchase(id);
    }

    @PatchMapping("/update/purchase/client/{idOrder}")
    @ResponseBody
    public OrderEntity updatePurchase(
            @RequestBody OrderEntity order,
            @PathVariable(name = "idOrder") Long idOrder) {
        return adminService.updatePurchase(order, idOrder);
    }

    @GetMapping("/show/seller/orders/qty")
    public void showOrdersQtySeller() {

    }

    @GetMapping("/show/sellers/sales/period/{idSeller}")
    public String showSellerSalesInPeriodRange(@PathVariable Long idSeller, @RequestParam LocalDate first, @RequestParam LocalDate second) {
        return adminService.checkNumberOfSalesSeller(idSeller, first, second);
    }

    @GetMapping("/show/allSales/period")
    public void showAllSalesInPerioRange() {

    }


    @Operation(summary = "Find the most sold vehicle over a given period of time.")
    @ApiResponse(responseCode = "200", description = "The statistics were successfully retrieved.")
    @ApiResponse(responseCode = "400", description = "One or more invalid dates were passed to the controller.")
    @GetMapping("/show/mostsold/period")
    public @ResponseBody ResponseEntity<ShowMostSoldCarInPeriodRangeResponse> showMostSoldCarInPeriodRange(
            @Parameter(description = "First range date", example = "YYYY-MM-DDThh:mm:ss", required = true, name = "firstDate") @RequestParam LocalDateTime firstDate,
            @Parameter(description = "Second range date", example = "YYYY-MM-DDThh:mm:ss", required = true, name = "secondDate") @RequestParam LocalDateTime secondDate){
        return adminService.showMostSoldCarInPeriodRange(firstDate, secondDate);
    }

    @Operation(summary = "Find the most expensive vehicle sold over a given period of time.")
    @ApiResponse(responseCode = "200", description = "The statistics were successfully retrieved.")
    @ApiResponse(responseCode = "400", description = "One or more invalid dates were passed to the controller.")
    @GetMapping("/show/mostexpensivesold/period")
    public @ResponseBody ResponseEntity<ShowMostExpensiveCarSoldInPeriodRangeResponse> showMostExpensiveCarInPeriodRange(
            @Parameter(description = "First range date", example = "YYYY-MM-DDThh:mm:ss", required = true, name = "firstDate") @RequestParam LocalDateTime firstDate,
            @Parameter(description = "Second range date", example = "YYYY-MM-DDThh:mm:ss", required = true, name = "secondDate") @RequestParam LocalDateTime secondDate) {
        return adminService.showMostExpensiveCarInPeriodRange(firstDate, secondDate);
    }

    @Operation(summary = "Find the most sold vehicle ever")
    @ApiResponse(responseCode = "200", description = "The statistics were successfully retrieved.")
    @GetMapping("/show/mostsoldever")
    public @ResponseBody VehicleSalesInfoDto showMostSoldCarEver() {
        return adminService.showMostSoldCarEver();
    }

    @Operation(summary = "Delete Client Account by Admin")
    @ApiResponse(responseCode = "200", description = "Client deleted")
    @ApiResponse(responseCode = "404", description = "Client not found")
    @DeleteMapping("/delete/client/{id}")
    public ResponseEntity<String> deleteSingleUser(
            @Parameter(description = "Client ID", example = "1", required = true, name = "id") @PathVariable(name = "id") Long idClient
    ) {
        return adminService.deleteClientbyAdmin(idClient);
    }

    @Operation(summary = "Update Client Account by Admin")
    @ApiResponse(responseCode = "404", description = "Client not found")
    @ApiResponse(responseCode = "512", description = "Wrong account type ")
    @ApiResponse(responseCode = "513", description = "Update successful")
    @ApiResponse(responseCode = "514", description = "Email already exists")
    @PatchMapping("/update/user/{id}")
    public ResponseEntity<UpdateClientbyAdminResponse> updateSingleUser(
            @Parameter(description = "Client ID", example = "1", required = true, name = "id") @PathVariable(name = "id") Long idClient,
            @RequestBody ClientEntity clientEntity
    ) {
        return adminService.updateClientbyAdmin(clientEntity, idClient);
    }

    @Operation(summary = "Delete Seller Account by Admin")
    @ApiResponse(responseCode = "200", description = "Seller deleted")
    @ApiResponse(responseCode = "404", description = "Seller not found")
    @DeleteMapping("/delete/seller/{id}")
    public ResponseEntity<String> deleteSingleSeller(
            @Parameter(description = "Seller ID", example = "1", required = true, name = "id") @PathVariable(name = "id") Long id
    ) {
        return adminService.deleteSellerbyAdmin(id);
    }

    @Operation(summary = "Update Seller Account by Admin")
    @ApiResponse(responseCode = "404", description = "Seller not found")
    @ApiResponse(responseCode = "512", description = "Wrong account type ")
    @ApiResponse(responseCode = "513", description = "Update successful")
    @ApiResponse(responseCode = "514", description = "Email already exists")
    @PatchMapping("/update/seller/{id}")
    public ResponseEntity<UpdateSellerbyAdminResponse> updateSingleSeller(
            @Parameter(description = "Seller ID", example = "1", required = true, name = "id") @PathVariable(name = "id") Long id,
            @RequestBody SellerEntity sellerEntity
    ) {
            return adminService.updateSellerbyAdmin(sellerEntity,id);
    }

    @Operation(summary = "Create a new Vehicle")
    @ApiResponse(responseCode = "201", description = "Vehicle created")
    @PostMapping("/create/vehicle") // POST CREAZIONE VEICOLO
    public ResponseEntity<CreateVehicleAdminResponse> createVehicle(
            @RequestBody VehicleEntity vehicle,
            @Parameter(description = "Vehicle type", example = "Truck", required = true, name = "type") @RequestParam String type) {
        return adminService.newVehicle(vehicle, type);
    }

    @Operation(summary = "Show Vehicles")
    @ApiResponse(responseCode = "200", description = "Show All Vehicles")
    @ApiResponse(responseCode = "404", description = "Vehicles not found")
    @GetMapping("/show/list/vehicle") // GET TUTTI I VEICOLI
    public ResponseEntity<ShowListVehicleAdminResponse> showAllVehicles(@AuthenticationPrincipal UserEntity user) {
        return adminService.showVehicles(user);
    }

    @Operation(summary = "Show Vehicle by ID")
    @ApiResponse(responseCode = "200", description = "Vehicle found by ID")
    @ApiResponse(responseCode = "404", description = "Vehicle not found")
    @GetMapping("/show/vehicle/{id}") // GET VEICOLO TRAMITE ID
    public ResponseEntity<ShowVehicleAdminResponse> getVehicleById(
            @Parameter(description = "Vehicle ID", example = "1", required = true, name = "id") @PathVariable(name = "id") Long idVehicle) {

        return adminService.showVehiclebyId(idVehicle);
    }

    @Operation(summary = "Update Vehicle by ID")
    @ApiResponse(responseCode = "200", description = "Vehicle updated")
    @ApiResponse(responseCode = "404", description = "Vehicle not found")
    @PatchMapping("/update/vehicle/{id}") // PATCH MODIFICA VEICOLO
    public ResponseEntity<UpdateVehicleAdminResponse> updateVehicle(
            @Parameter(description = "Vehicle ID", example = "1", required = true, name = "id") @PathVariable(name = "id") Long idVehicle,
            @Parameter(description = "Vehicle", required = true, name = "vehicle") @RequestBody VehicleEntity vehicle) {
        return adminService.updateVehicle(vehicle, idVehicle);
    }

    @Operation(summary = "Delete Vehicle by ID")
    @ApiResponse(responseCode = "200", description = "Vehicle deleted")
    @ApiResponse(responseCode = "404", description = "Vehicle not found")
    @DeleteMapping("/delete/vehicle/{id}") // DELETE VEICOLO
    public ResponseEntity<DeleteVehicleResponse> deleteVehicle(
            @Parameter(description = "Vehicle ID", example = "1", required = true, name = "id") @PathVariable(name = "id") Long id) {
        return adminService.deleteVehicle(id);
    }


    @Operation(summary = "Show earnings over a given time range.")
    @ApiResponse(responseCode = "200", description = "Show earnings.")
    @ApiResponse(responseCode = "400", description = "One or more invalid dates were passed to the controller.")
    @GetMapping("/show/earnings/period")
    public @ResponseBody ResponseEntity<ShowEarningsInPeriodRangeResponse> showEarningsInPeriodRange(
            @Parameter(description = "First range date", example = "YYYY-MM-DDThh:mm:ss", required = true, name = "firstDate") @RequestParam LocalDateTime firstDate,
            @Parameter(description = "Second range date", example = "YYYY-MM-DDThh:mm:ss", required = true, name = "secondDate") @RequestParam LocalDateTime secondDate) {
        return adminService.showEarningsInPeriodRange(firstDate, secondDate);
    }


    @Operation(summary = "Show vehicles filtered by their sell type.")
    @ApiResponse(responseCode = "200", description = "Show filtered vehicles.")
    @ApiResponse(responseCode = "404", description = "No vehicles matching your query have been found.")
    @GetMapping("/show/list/vehicle/byfilter")
    public @ResponseBody ResponseEntity<ShowListVehicleAdminResponse> showFilteredVehicles(
            @Parameter(description = "Sell type", example = "Possible values: RFD, USED, ORDERABLE", required = true, name = "sellType") @RequestParam(name = "selltype", required = true) String sellType) {
        return adminService.showFilteredVehicles(sellType);
    }

}
