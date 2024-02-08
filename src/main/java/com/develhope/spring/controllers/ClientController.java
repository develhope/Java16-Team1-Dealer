package com.develhope.spring.controllers;

import com.develhope.spring.dto.order.OrderClientDTO;
import com.develhope.spring.dto.order.PurchaseClientDTO;
import com.develhope.spring.entities.order.OrderEntity;
import com.develhope.spring.entities.rent.RentEntity;
import com.develhope.spring.entities.user.ClientEntity;
import com.develhope.spring.entities.vehicle.VehicleEntity;
import com.develhope.spring.response.order.ListOrderResponse;
import com.develhope.spring.response.order.OrderResponse;
import com.develhope.spring.response.order.StatusCancelledResponse;
import com.develhope.spring.response.purchase.ListPurchaseResponse;
import com.develhope.spring.response.purchase.PurchaseResponse;
import com.develhope.spring.services.ClientService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/v1/client")
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
            @RequestBody(required = true) OrderClientDTO orderClientDTO) {
        return clientService.createOrder(orderClientDTO);
    }

    @Operation(summary = "Get all Orders")
    @ApiResponse(responseCode = "200", description = "Orders found")
    @ApiResponse(responseCode = "404", description = "Orders not found")
    @GetMapping("/show/order/list")
    @ResponseBody
    public ResponseEntity<ListOrderResponse> orderEntityList() {
        return clientService.orderEntityList();
    }

    @Operation(summary = "Update Order Status To Cancelled")
    @ApiResponse(responseCode = "200", description = "Order status cancelled")
    @ApiResponse(responseCode = "404", description = "Order not found")
    @PutMapping("/update/status/order/cancelled/{idOrder}")
    @ResponseBody
    public ResponseEntity<StatusCancelledResponse> updateStatusCancelledId(@PathVariable(name = "idOrder") Long id) {
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
            @RequestBody(required = true) PurchaseClientDTO purchaseClientDTO) {
        return clientService.createPurchase(purchaseClientDTO);
    }

    @Operation(summary = "Get all Purchases")
    @ApiResponse(responseCode = "200", description = "Purchases found")
    @ApiResponse(responseCode = "404", description = "Purchases not found")
    @GetMapping("/show/purchase/list")
    @ResponseBody
    public ResponseEntity<ListPurchaseResponse> showPurchases() {
        return clientService.purchaseList();
    }

    @PostMapping("/create/rent")
    @ResponseBody
    public RentEntity newRent(@RequestBody(required = true) RentEntity rent,
                              @RequestParam(name = "id_seller", required = true) Long idSeller,
                              @RequestParam(name = "id_client", required = true) Long idClient,
                              @RequestParam(name = "id_vehicle", required = true) Long idVehicle) {
        return clientService.newRent(rent, idSeller, idClient, idVehicle);
    }

    @GetMapping("/show/rent/list")
    public @ResponseBody List<RentEntity> showRents() {
        return clientService.showRents();
    }

    @DeleteMapping("/delete/rent/{id}")
    public void deleteRent(@PathVariable Long id) {
        clientService.deleteRent(id);
    }
    @Operation(summary = "Delete My Account")
    @DeleteMapping("/delete/myaccount")
    public ResponseEntity<ClientEntity> deleteClient() {
        return clientService.deleteAccount();
    }
    @Operation(summary = "Update My Account")
    @PatchMapping("/upgrade/myaccount")
    @ResponseBody
    public ClientEntity updateClient(@RequestBody ClientEntity updClient) {
        return clientService.updateAccount(updClient);
    }
    @Operation(summary = "Show Vehicle by ID")
    @GetMapping("/show/vehicle/{idVehicle}")
    @ResponseBody
    public Optional<VehicleEntity> showVehicleID(@PathVariable(name = "idVehicle") Long idVehicle) {
        return clientService.showVehicleID(idVehicle);
    }
    @Operation(summary = "Show Vehicle by Filter")
    @GetMapping("/show/list/vehicle/by/filter")
    @ResponseBody
    public List<VehicleEntity> showVehicle(
            @RequestParam(name = "brand", required = false) String brand,
            @RequestParam(name = "model", required = false) String model,
            @RequestParam(name = "color", required = false) String color) {
        return clientService.showAllVehiclesFilteted(color, brand, model);
    }
    @Operation(summary = "Show Vehicle by Range Price")
    @GetMapping("/show/list/vehicle/by/rangeprice")
    @ResponseBody
    public List<VehicleEntity> findVehicleByRangePrice(
            @RequestParam(name = "minPrice", required = true) BigDecimal minPrice,
            @RequestParam(name = "maxPrice", required = true) BigDecimal maxPrice) {
        return clientService.filterFindVehicleByRangePrice(minPrice, maxPrice);
    }

}

