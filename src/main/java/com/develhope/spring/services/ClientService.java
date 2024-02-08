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
import com.develhope.spring.response.order.ListOrderResponse;
import com.develhope.spring.response.order.OrderResponse;
import com.develhope.spring.response.purchase.PurchaseResponse;
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

    public ResponseEntity<ClientEntity> deleteAccount() {
        ClientEntity client = clientRepository.findById(idLogin.getId()).get();
        clientRepository.delete(client);
        return ResponseEntity.ok(client);
    }

    public ClientEntity updateAccount(ClientEntity clientEntity) {
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

        return clientRepository.save(client);

    }

    public Optional<VehicleEntity> showVehicleID(Long idVehicle) {
        Optional<VehicleEntity> vehicle = vehicleRepository.findById(idVehicle);
        if (vehicle.isPresent()) {
            return vehicle;
        } else {
            return Optional.empty();
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

    public OrderEntity updateStatusCancelled(Long idOrder) {
        orderRepository.updateStatusCancelledOrderWithId(idOrder);
        return orderRepository.findById(idOrder).get();
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


    public List<OrderEntity> purchaseList() {
        return orderRepository.showListPurchase(idLogin.getId());

    }

    public List<VehicleEntity> filterFindVehicleByRangePrice(BigDecimal minPrice, BigDecimal maxPrice) {
        BigDecimal[] price = {minPrice, maxPrice};
        Arrays.sort(price);
        return vehicleRepository.showAllVehiclesByRangePrice(price[0], price[1]);
    }

    public List<VehicleEntity> showAllVehiclesFilteted(String color, String brand, String model) {
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
            return vehicleRepository.findAll();
        } else {
            return vehicleRepository.showAllVehiclesFiltered(color, brand, model);
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
