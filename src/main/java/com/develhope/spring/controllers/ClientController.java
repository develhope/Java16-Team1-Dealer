package com.develhope.spring.controllers;

import com.develhope.spring.dto.order.OrderClientDTO;
import com.develhope.spring.dto.order.PurchaseClientDTO;
import com.develhope.spring.entities.order.OrderEntity;
import com.develhope.spring.entities.rent.RentEntity;
import com.develhope.spring.entities.user.ClientEntity;
import com.develhope.spring.entities.vehicle.VehicleEntity;
import com.develhope.spring.services.ClientService;
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

    @PostMapping("/create/order")
    @ResponseBody
    public OrderEntity newOrder(
            @RequestBody(required = true) OrderClientDTO orderClientDTO) {
        return clientService.newOrder(orderClientDTO);
    }

    @GetMapping("/show/order/list")
    public @ResponseBody List<OrderEntity> orderEntityList() {
        return clientService.orderEntityList();
    }

    @PutMapping("/update/status/order/cancelled/{idOrder}")
    public @ResponseBody OrderEntity updateStatusCancelledId(@PathVariable(name = "idOrder") Long id) {
        return clientService.updateStatusCancelled(id);
    }

    @PostMapping("/create/purchase")
    @ResponseBody
    public OrderEntity createPurchase(
            @RequestBody(required = true)PurchaseClientDTO purchaseClientDTO) {
        return clientService.newPurchase(purchaseClientDTO);
    }

    @GetMapping("/show/purchase/list")
    public @ResponseBody List<OrderEntity> showPurchases() {
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

    @DeleteMapping("/delete/myaccount")
    public ResponseEntity<ClientEntity> deleteClient() {
        return clientService.deleteAccount();
    }

    @PatchMapping("/upgrade/myaccount")
    @ResponseBody
    public ClientEntity updateClient(@RequestBody ClientEntity updClient) {
        return clientService.updateAccount(updClient);
    }

    @GetMapping("/show/vehicle/{idVehicle}")
    @ResponseBody
    public Optional<VehicleEntity> showVehicleID(@PathVariable(name = "idVehicle") Long idVehicle) {
        return clientService.showVehicleID(idVehicle);
    }

    @GetMapping("/show/list/vehicle/by/filter")
    public @ResponseBody VehicleEntity showVehicle() {
        return new VehicleEntity();
    }

    // DA RIVEDERE NON LO GESTIREI IN QUESTO MODO
    @GetMapping("/show/list/vehicle/by/rangeprice")
    @ResponseBody
    public List<VehicleEntity> findVehicleByRangePrice(@RequestParam(name = "minPrice") BigDecimal minPrice, @RequestParam(name = "maxPrice") BigDecimal maxPrice) {
        return clientService.filterFindVehicleByRangePrice(minPrice, maxPrice);
    }

    @GetMapping("/findbycolor")
    public void findVehicleByColor() {
    }

    @GetMapping("/findbymodel")
    public void findVehicleByModel() {
    }

    @GetMapping("/findbybrand")
    public void findVehicleByBrand() {
    }

}

