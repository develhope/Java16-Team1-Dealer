package com.develhope.spring.controllers;

import com.develhope.spring.dto.IdLogin;
import com.develhope.spring.entities.order.OrderEntity;
import com.develhope.spring.entities.rent.RentEntity;
import com.develhope.spring.entities.user.ClientEntity;
import com.develhope.spring.entities.vehicle.VehicleEntity;
import com.develhope.spring.services.ClientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/v1/client")
public class ClientController {
    @Autowired
    private IdLogin idLogin;

    @Autowired
    private ClientService clientService;

    @PostMapping("/create/order")
    public @ResponseBody OrderEntity newOrder(
            @RequestBody(required = true) OrderEntity order,
            @RequestParam(name = "id_seller" , required = true) Long idSeller,
            @RequestParam(name = "id_vehicle", required = true) Long idVehicle) {

        return clientService.newOrder(order, idSeller, idVehicle, idLogin.getId());
    }

    @GetMapping("/show/order/list")
    public @ResponseBody List<OrderEntity> orderEntityList() {
        return null;
    }

    @DeleteMapping("/delete/order/{id}")
    public void deleteOrder(@PathVariable Long id) {
    }

    @PostMapping("/create/purchase")
    public void newPurchase(@RequestBody VehicleEntity purchasableVehicle) {
    }

    @GetMapping("/show/purchase/list")
    public void showPurchases(@PathVariable Long id) {
    }

    @PostMapping("/create/rent")
    public @ResponseBody RentEntity newRent (@RequestBody RentEntity rent) {
        return new RentEntity();
    }

    @GetMapping("/show/rent/list")
    public void showRents (@PathVariable Long id) {
    }

    @DeleteMapping("/delete/rent/{id}")
    public void deleteRent(@PathVariable Long id) {
    }

    @DeleteMapping("/delete/myaccount")
    public void deleteClient(@PathVariable Long id) {
    }

    @PutMapping("/upgrade/myaccount")
    public @ResponseBody ClientEntity updateClient (@PathVariable Long id, @RequestBody ClientEntity updClient) {
    return new ClientEntity();
    }

    @GetMapping("/show/list/vehicle/by/filter")
    public @ResponseBody VehicleEntity showVehicle() {
        return new VehicleEntity();
    }

    // DA RIVEDERE NON LO GESTIREI IN QUESTO MODO
    @GetMapping("/findbyprice")
    public void findVehicleByPrice() {
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
/*

    Un cliente potrà:

        Creare un ordine a partire da un veicolo contrassegnato come ordinabile
        Vedere i propri ordini
        Cancellare un ordine
        Creare un acquisto a partire da un veicolo contrassegnato come acquistabile
        Vedere i propri acquisti
        Creare un noleggio
        Vedere i propri noleggi
        Cancellare un noleggio
        Cancellare la propria utenza
        Modificare i dati dell’utente
        Ricercare un veicolo secondo diversi criteri (prezzo, colore, marca, modello, ecc)
        Ottenere i dettagli di un veicolo specifico*/
