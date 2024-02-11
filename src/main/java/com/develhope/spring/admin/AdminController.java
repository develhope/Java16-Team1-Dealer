package com.develhope.spring.admin;

import com.develhope.spring.admin.adminControllerResponse.*;
import com.develhope.spring.client.ClientEntity;
import com.develhope.spring.order.*;
import com.develhope.spring.rent.*;
import com.develhope.spring.vehicle.VehicleEntity;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.Optional;

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
    public RentEntity createRent(@RequestBody RentDto rentDto) {
        return adminService.createRent(rentDto);
    }

    @DeleteMapping("/delete/client/rent/{id}")
    public RentEntity deleteRent(@PathVariable Long id) {
        return adminService.deleteRent(id);
    }

    @PatchMapping("/update/client/rent/{id}")
    public RentEntity updateRent(@PathVariable Long id, @RequestBody RentDto rentDto) {
        return adminService.updateRent(id, rentDto);
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

    @GetMapping("/show/vehicle/orderable")
    public void showAvailableVehicleOrderable() {

    }

    @GetMapping("/show/vehicle/rfdNew")
    public void showAvailableVehiclerfdNew() {

    }

    @GetMapping("/show/vehicle/rfdUsed")
    public void showAvailableVehiclerfdUsed() {

    }

    @GetMapping("/show/vehicle/used")
    public void showAvailableVehicleused() {

    }

    @GetMapping("/show/vehicle/notAvaiable")
    public void showAllVehicleNotAvailable() {

    }

    @DeleteMapping("/delete/user/{id}")
    public void deleteSingleUser() {

    }

    @Operation(summary = "Update Client Account by Admin")
    @ApiResponse(responseCode = "201", description = "Order created")
    @ApiResponse(responseCode = "600", description = "Vehicle does not exist")
    @ApiResponse(responseCode = "601", description = "Seller does not exist")
    @ApiResponse(responseCode = "602", description = "Vehicle is not orderable")
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

    @PatchMapping("/update/seller/{id}")
    public void updateSingleSeller() {

    }

    @GetMapping("/show/vehicle/bestSeller/period")
    public void showVehicleBestSellerInPeriodRange() {

    }

    @GetMapping("/show/vehicle/maxPrice")
    public void showVehicleMaxPrice() {

    }

    @GetMapping("/show/vehicle/maxBuyer")
    public void showVehicleMaxBuyer() {

    }

    @Operation(summary = "Create a new Vehicle")
    @ApiResponse(responseCode = "201", description = "Vehicle created")
    @PostMapping("/create/vehicle") // POST CREAZIONE VEICOLO
    public ResponseEntity<CreateVehicleAdminResponse> createVehicle(
            @RequestBody VehicleEntity vehicle,
            @Parameter (description = "Vehicle type", example = "Truck", required = true, name = "type") @RequestParam String type) {
        return adminService.newVehicle(vehicle, type);
    }
    @Operation(summary = "Show Vehicles")
    @ApiResponse(responseCode = "200", description = "Show All Vehicles")
    @ApiResponse(responseCode = "404", description = "Vehicles not found")
    @GetMapping("/show/list/vehicle") // GET TUTTI I VEICOLI
    public ResponseEntity<ShowListVehicleAdminResponse> getVehicleById() {
        return adminService.showVehicles();
    }
    @Operation(summary = "Show Vehicle by ID")
    @ApiResponse(responseCode = "200", description = "Vehicle found by ID")
    @ApiResponse(responseCode = "404", description = "Vehicle not found")
    @GetMapping("/show/vehicle/{id}") // GET VEICOLO TRAMITE ID
    public ResponseEntity<ShowVehicleAdminResponse> getVehicleById(
            @Parameter (description = "Vehicle ID", example = "1", required = true, name = "id") @PathVariable(name = "id") Long idVehicle) {

        return adminService.showVehiclebyId(idVehicle);
    }
    @Operation(summary = "Update Vehicle by ID")
    @ApiResponse(responseCode = "200", description = "Vehicle updated")
    @ApiResponse(responseCode = "404", description = "Vehicle not found")
    @PatchMapping("/update/vehicle/{id}") // PATCH MODIFICA VEICOLO
    public ResponseEntity<UpdateVehicleAdminResponse> updateVehicle (
            @Parameter (description = "Vehicle ID", example = "1", required = true, name = "id") @PathVariable(name = "id") Long idVehicle,
            @Parameter (description = "Vehicle", required = true, name = "vehicle") @RequestBody VehicleEntity vehicle) {
        return adminService.updateVehicle(vehicle, idVehicle);
    }
    @Operation(summary = "Delete Vehicle by ID")
    @ApiResponse(responseCode = "200", description = "Vehicle deleted")
    @ApiResponse(responseCode = "404", description = "Vehicle not found")
    @DeleteMapping("/delete/vehicle/{id}") // DELETE VEICOLO
    public ResponseEntity<DeleteVehicleResponse> deleteVehicle(
            @Parameter (description = "Vehicle ID", example = "1", required = true, name = "id") @PathVariable(name = "id") Long id) {
        return adminService.deleteVehicle(id);
    }


}
