package com.develhope.spring.services;

import com.develhope.spring.dto.IdLogin;
import com.develhope.spring.dto.OrderClientDTO;
import com.develhope.spring.entities.order.OrderEntity;
import com.develhope.spring.entities.order.OrderType;
import com.develhope.spring.entities.rent.RentEntity;
import com.develhope.spring.entities.user.ClientEntity;
import com.develhope.spring.entities.user.SellerEntity;
import com.develhope.spring.entities.vehicle.SellType;
import com.develhope.spring.entities.vehicle.VehicleEntity;
import com.develhope.spring.repositories.*;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
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
        if(clientEntity.getName() != null){
            client.setName(clientEntity.getName());
        }
        if(clientEntity.getSurname() != null){
            client.setSurname(clientEntity.getSurname());
        }
        if(clientEntity.getPhone() != null){
            client.setPhone(clientEntity.getPhone());
        }
        if(clientEntity.getPsw() != null){
            client.setPsw(clientEntity.getPsw());
        }

        return clientRepository.save(client);

    }

    public Optional<VehicleEntity> showVehicleID(Long idVehicle) {
        Optional<VehicleEntity> vehicle = vehicleRepository.findById(idVehicle);
        if(vehicle.isPresent()){
            return vehicle;
        }else{
            return Optional.empty();
        }
    }


    public OrderEntity createOrder(OrderClientDTO orderClientDTO) {
        VehicleEntity vehicle = vehicleRepository.findById(orderClientDTO.getIdVehicle()).get();
        ClientEntity client = clientRepository.findById(idLogin.getId()).get();
        SellerEntity seller = sellerRepository.findById(orderClientDTO.getIdSeller()).get();
        if (vehicle.getSellType().equals(SellType.ORDERABLE) && vehicle != null) {
            OrderEntity newOrder = new OrderEntity();
            newOrder.setOrderType(OrderType.ORDER);
            newOrder.setOrderState(orderClientDTO.getOrderState());
            newOrder.setAdvPayment(orderClientDTO.getAdvPayment());
            newOrder.setIsPaid(orderClientDTO.getIsPaid());
            newOrder.setVehicleId(vehicle);
            newOrder.setClientId(client);
            newOrder.setSellerId(seller);
            return newOrder;
        } else {
            return null;
        }

    }
    public OrderEntity newOrder(OrderClientDTO orderClientDTO) {
            OrderEntity order = createOrder(orderClientDTO);
            if (order != null) {
                return orderRepository.save(order);
            } else {
                return null;
            }

    }

    public List<OrderEntity> orderEntityList() {
            return orderRepository.showListOrder(idLogin.getId());

    }

    public OrderEntity updateStatusCancelled(Long idOrder) {
        orderRepository.updateStatusCancelledOrderWithId(idOrder);
        return orderRepository.findById(idOrder).get();
    }

    public OrderEntity createPurchase(OrderEntity orderEntity, Long idSeller, Long idVehicle, Long idClient) {
        VehicleEntity vehicle = vehicleRepository.findById(idVehicle).get();
        if (vehicle.getSellType().equals(SellType.RFD)) {
            OrderEntity newOrder = new OrderEntity();
            newOrder.setOrderType(OrderType.PURCHASE);
            newOrder.setOrderState(orderEntity.getOrderState());
            newOrder.setAdvPayment(orderEntity.getAdvPayment());
            newOrder.setIsPaid(orderEntity.getIsPaid());
            newOrder.setVehicleId(vehicle);
            newOrder.setClientId(clientRepository.findById(idClient).get());
            newOrder.setSellerId(sellerRepository.findById(idSeller).get());
            return newOrder;
        } else {
            return null;
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

    public OrderEntity newPurchase(OrderEntity orderEntity, Long idSeller, Long idVehicle) {

            OrderEntity purchase = createPurchase(orderEntity, idSeller, idVehicle, idLogin.getId());
            if (purchase != null) {
                return orderRepository.save(purchase);
            } else {
                return null;
            }


    }

    public List<OrderEntity> purchaseList() {
            return orderRepository.showListPurchase(idLogin.getId());

    }

    public List<RentEntity> showRents() {
            return rentRepository.showRentList(idLogin.getId());
    }

    public RentEntity newRent(RentEntity rentEntity, Long idSeller, Long idClient, Long idVehicle) {
            RentEntity rent = createRent(rentEntity,idSeller,idClient,idVehicle);
            if(rent != null) {
                return rentRepository.save(rent);
            } else {
                return null;
            }

    }


    public void deleteRent(Long id) {
            rentRepository.customDeleteById(idLogin.getId(),id);
    }
}
