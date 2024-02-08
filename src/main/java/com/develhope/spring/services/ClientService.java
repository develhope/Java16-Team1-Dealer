package com.develhope.spring.services;

import com.develhope.spring.dto.IdLogin;
import com.develhope.spring.dto.order.OrderClientDTO;
import com.develhope.spring.dto.order.PurchaseClientDTO;
import com.develhope.spring.entities.order.OrderEntity;
import com.develhope.spring.entities.order.OrderType;
import com.develhope.spring.entities.rent.RentEntity;
import com.develhope.spring.entities.user.ClientEntity;
import com.develhope.spring.entities.user.SellerEntity;
import com.develhope.spring.entities.vehicle.SellType;
import com.develhope.spring.entities.vehicle.VehicleEntity;
import com.develhope.spring.repositories.*;
import com.develhope.spring.response.clientControllerResponse.*;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@Transactional
@Service
public class ClientService {
    @Autowired
    private IdLogin idLogin;
    @Autowired
    private ClientRepository clientRepository;
    @Autowired
    private SellerRepository sellerRepository;
    @Autowired
    private VehicleRepository vehicleRepository;
    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private RentRepository rentRepository;

    public ResponseEntity<String> deleteAccount() {
        clientRepository.delete(clientRepository.findById(idLogin.getId()).get());
        return ResponseEntity.status(200).body("Account deleted successfully");
    }

    public ResponseEntity<UpdateAccountResponse> updateAccount(ClientEntity clientEntity) {
        if(clientEntity != null) {
            ClientEntity client = clientRepository.findById(idLogin.getId()).get();
            if (clientEntity.getName() != null) {
                client.setName(clientEntity.getName());
            }
            if (clientEntity.getSurname() != null) {
                client.setSurname(clientEntity.getSurname());
            }
            if (clientEntity.getPhone() != null) {
                client.setPhone(clientEntity.getPhone());
            }
            if (clientEntity.getPsw() != null) {
                client.setPsw(clientEntity.getPsw());
            }
            String message = "Account updated successfully";
            UpdateAccountResponse updateAccountResponse = new UpdateAccountResponse(message,clientRepository.findById(idLogin.getId()).get());
            return ResponseEntity.status(607).body(updateAccountResponse);
        }else{
            String message = "Please enter details to update account";
            UpdateAccountResponse updateAccountResponse = new UpdateAccountResponse(message,clientRepository.findById(idLogin.getId()).get());
            return ResponseEntity.status(406).body(updateAccountResponse);
        }


    }

    public ResponseEntity<ShowVehicleIDResponse> showVehicleID(Long idVehicle) {
        VehicleEntity vehicle = new VehicleEntity();
        if (vehicleRepository.existsById(idVehicle)) {
            vehicle = vehicleRepository.findById(idVehicle).get();
            String message = "Vehicle found";
            ShowVehicleIDResponse showVehicleIDResponse = new ShowVehicleIDResponse(message, vehicle);
            return ResponseEntity.status(302).body(showVehicleIDResponse);
        } else {
            String message = "Vehicle not found";
            ShowVehicleIDResponse showVehicleIDResponse = new ShowVehicleIDResponse(message, vehicle);
            return ResponseEntity.status(404).body(showVehicleIDResponse);
        }
    }


    public ResponseEntity<OrderResponse> createOrder(OrderClientDTO orderClientDTO) {
        OrderEntity newOrder = new OrderEntity();
        VehicleEntity vehicle;
        SellerEntity seller;
        ClientEntity client = clientRepository.findById(idLogin.getId()).get();

        if (sellerRepository.existsById(orderClientDTO.getIdSeller())) {
            seller = sellerRepository.findById(orderClientDTO.getIdSeller()).get();
        } else {
            String message = "Seller with id " + orderClientDTO.getIdSeller() + " does not exist";
            OrderResponse orderResponse = new OrderResponse(message, newOrder);
            return ResponseEntity.status(601).body(orderResponse);
        }

        if (vehicleRepository.existsById(orderClientDTO.getIdVehicle())) {
            vehicle = vehicleRepository.findById(orderClientDTO.getIdVehicle()).get();
            if (vehicle.getSellType().equals(SellType.ORDERABLE)) {
                newOrder.setOrderType(OrderType.ORDER);
                newOrder.setOrderState(orderClientDTO.getOrderState());
                newOrder.setAdvPayment(orderClientDTO.getAdvPayment());
                newOrder.setIsPaid(orderClientDTO.getIsPaid());
                newOrder.setVehicleId(vehicle);
                newOrder.setClientId(client);
                newOrder.setSellerId(seller);
                orderRepository.save(newOrder);
                String message = "Order created";
                OrderResponse orderResponse = new OrderResponse(message, newOrder);
                return ResponseEntity.status(201).body(orderResponse);
            } else {
                String message = "Vehicle with id " + orderClientDTO.getIdVehicle() + " is not orderable";
                OrderResponse orderResponse = new OrderResponse(message, newOrder);
                return ResponseEntity.status(602).body(orderResponse);
            }
        } else {
            String message = "Vehicle with id " + orderClientDTO.getIdVehicle() + " does not exist";
            OrderResponse orderResponse = new OrderResponse(message, newOrder);
            return ResponseEntity.status(600).body(orderResponse);
        }
    }

    public ResponseEntity<ListOrderResponse> orderEntityList() {
        List<OrderEntity> listOrder = orderRepository.showListOrder(idLogin.getId());
        if (listOrder.size() > 0) {
            String message = "Orders found";
            ListOrderResponse listOrderResponse = new ListOrderResponse(message, listOrder);
            return ResponseEntity.status(200).body(listOrderResponse);
        } else {
            String message = "Orders not found";
            ListOrderResponse listOrderResponse = new ListOrderResponse(message, Arrays.asList());
            return ResponseEntity.status(404).body(listOrderResponse);
        }
    }

    public ResponseEntity<StatusCancelledResponse> updateStatusCancelled(Long idOrder) {
        OrderEntity order = new OrderEntity();
        if(!orderRepository.existsById(idOrder)) {
            String message = "Order with id " + idOrder + " does not exist";
            StatusCancelledResponse statusCancelledResponse = new StatusCancelledResponse(message, order);
            return ResponseEntity.status(404).body(statusCancelledResponse);
        }else {
            orderRepository.updateStatusCancelledOrderWithId(idOrder);
            order = orderRepository.findById(idOrder).get();
            String message = "Order with id " + idOrder + " cancelled";
            StatusCancelledResponse statusCancelledResponse = new StatusCancelledResponse(message, order);
            return ResponseEntity.status(200).body(statusCancelledResponse);
        }
    }

    public ResponseEntity<PurchaseResponse> createPurchase(PurchaseClientDTO purchaseClientDTO) {
        OrderEntity newOrder = new OrderEntity();
        VehicleEntity vehicle;
        SellerEntity seller;
        ClientEntity client = clientRepository.findById(idLogin.getId()).get();

        if (sellerRepository.existsById(purchaseClientDTO.getIdSeller())) {
            seller = sellerRepository.findById(purchaseClientDTO.getIdSeller()).get();
        } else {
            String message = "Seller with id " + purchaseClientDTO.getIdSeller() + " does not exist";
            PurchaseResponse purchaseResponse = new PurchaseResponse(message, newOrder);
            return ResponseEntity.status(601).body(purchaseResponse);
        }

        if (vehicleRepository.existsById(purchaseClientDTO.getIdVehicle())) {
            vehicle = vehicleRepository.findById(purchaseClientDTO.getIdVehicle()).get();
            if (vehicle.getSellType().equals(SellType.RFD)) {
                newOrder.setOrderType(OrderType.PURCHASE);
                newOrder.setOrderState(purchaseClientDTO.getOrderState());
                newOrder.setAdvPayment(purchaseClientDTO.getAdvPayment());
                newOrder.setIsPaid(purchaseClientDTO.getIsPaid());
                newOrder.setVehicleId(vehicle);
                newOrder.setClientId(client);
                newOrder.setSellerId(seller);
                orderRepository.save(newOrder);
                String message = "Purchase created";
                PurchaseResponse purchaseResponse = new PurchaseResponse(message, newOrder);
                return ResponseEntity.status(201).body(purchaseResponse);
            } else {
                String message = "Vehicle with id " + purchaseClientDTO.getIdVehicle() + " is not purchasable";
                PurchaseResponse purchaseResponse = new PurchaseResponse(message, newOrder);
                return ResponseEntity.status(602).body(purchaseResponse);
            }
        } else {
            String message = "Vehicle with id " + purchaseClientDTO.getIdVehicle() + " does not exist";
            PurchaseResponse purchaseResponse = new PurchaseResponse(message, newOrder);
            return ResponseEntity.status(600).body(purchaseResponse);
        }
    }

    public ResponseEntity<ListPurchaseResponse> purchaseList() {
        List<OrderEntity> listOrder = orderRepository.showListPurchase(idLogin.getId());;
        if (listOrder.size() > 0) {
            String message = "Purchases found";
            ListPurchaseResponse listPurchaseResponse = new ListPurchaseResponse(message, listOrder);
            return ResponseEntity.status(200).body(listPurchaseResponse);
        } else {
            String message = "Purchases not found";
            ListPurchaseResponse listPurchaseResponse = new ListPurchaseResponse(message, Arrays.asList());
            return ResponseEntity.status(404).body(listPurchaseResponse);
        }
    }

    public ResponseEntity<ListVehicleFilterResponse> filterFindVehicleByRangePrice(BigDecimal minPrice, BigDecimal maxPrice) {
        BigDecimal[] price = {minPrice, maxPrice};
        Arrays.sort(price);
        List<VehicleEntity> vehicleListFilterPrice = vehicleRepository.showAllVehiclesByRangePrice(price[0], price[1]);
        if(vehicleListFilterPrice.size() > 0){
            String message = "Vehicles found";
            ListVehicleFilterResponse listVehicleFilterResponse = new ListVehicleFilterResponse(message, vehicleListFilterPrice);
            return ResponseEntity.status(302).body(listVehicleFilterResponse);
        }else{
            String message = "Vehicles not found by range price";
            ListVehicleFilterResponse listVehicleFilterResponse = new ListVehicleFilterResponse(message, Arrays.asList());
            return ResponseEntity.status(611).body(listVehicleFilterResponse);
        }
    }

    public ResponseEntity<ListVehicleFilterResponse> showAllVehiclesFilteted(String color, String brand, String model) {
        if (color == null) {
            color = " ";
        }
        if (brand == null) {
            brand = " ";
        }
        if (model == null) {
            model = " ";
        }

        if (color == " " && brand == " " && model == " ") {
            String message = "No filter applied, showing all vehicles";
            ListVehicleFilterResponse listVehicleFilterResponse = new ListVehicleFilterResponse(message, vehicleRepository.findAll());
            return ResponseEntity.status(610).body(listVehicleFilterResponse);
        } else {
            String message = "Vehicles filtered";
            ListVehicleFilterResponse listVehicleFilterResponse = new ListVehicleFilterResponse(message, vehicleRepository.showAllVehiclesFiltered(color, brand, model));
            return ResponseEntity.status(302).body(listVehicleFilterResponse);
        }

    }


    public RentEntity createRent(RentEntity rentEntity, Long idSeller, Long idClient, Long idVehicle) {
        RentEntity rent = new RentEntity();
        rent.setClientId(clientRepository.findById(idClient).get());
        rent.setSellerId(sellerRepository.findById(idSeller).get());
        rent.setVehicleId(vehicleRepository.findById(idVehicle).get());
        rent.setStartingDate(LocalDateTime.now());
        rent.setEndingDate(rentEntity.getEndingDate());
        rent.setDailyFee(rentEntity.getDailyFee());
        rent.setTotalFee(rentEntity.getTotalFee());
        rent.setIsPaid(rentEntity.getIsPaid());
        return rent;
    }


    public List<RentEntity> showRents() {
        return rentRepository.showRentList(idLogin.getId());
    }

    public RentEntity newRent(RentEntity rentEntity, Long idSeller, Long idClient, Long idVehicle) {
        RentEntity rent = createRent(rentEntity, idSeller, idClient, idVehicle);
        if (rent != null) {
            return rentRepository.save(rent);
        } else {
            return null;
        }

    }


    public void deleteRent(Long id) {
        rentRepository.customDeleteById(idLogin.getId(), id);
    }
}
