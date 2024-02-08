package com.develhope.spring.services;

import com.develhope.spring.dto.IdLogin;
import com.develhope.spring.entities.order.OrderEntity;
import com.develhope.spring.entities.order.OrderState;
import com.develhope.spring.entities.order.OrderType;
import com.develhope.spring.entities.rent.RentEntity;
import com.develhope.spring.entities.vehicle.SellType;
import com.develhope.spring.entities.vehicle.VehicleEntity;
import com.develhope.spring.repositories.*;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class SellerService {

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


    public OrderEntity createOrder(OrderEntity orderEntity, Long idSeller, Long idVehicle, Long idClient) {
        VehicleEntity vehicle = vehicleRepository.findById(idVehicle).get();
        if (vehicle.getSellType().equals(SellType.ORDERABLE)) {
            OrderEntity newOrder = new OrderEntity();
            newOrder.setOrderType(OrderType.ORDER);
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

    public OrderEntity newOrder(OrderEntity orderEntity, Long idClient, Long idVehicle) {
        OrderEntity order = createOrder(orderEntity, idLogin.getId(), idVehicle, idClient);
        if (order != null) {
            return orderRepository.save(order);
        } else {
            return null;
        }
    }


    public OrderEntity updateStatusCancelled(Long idOrder) {
        orderRepository.updateStatusCancelledOrderWithId(idOrder);
        return orderRepository.findById(idOrder).get();
    }

    public OrderEntity updateOrder(OrderEntity orderEntity, Long idOrder) {
        OrderEntity order = orderRepository.findById(idOrder).get();
        if (orderEntity.getOrderType() != null) {
            order.setOrderType(orderEntity.getOrderType());
        }
        if (orderEntity.getOrderState() != null) {
            order.setOrderState(orderEntity.getOrderState());
        }
        if (orderEntity.getAdvPayment() != null) {
            order.setAdvPayment(orderEntity.getAdvPayment());
        }
        return orderRepository.save(order);

    }

    public OrderEntity checkOrder(Long id) {
        return orderRepository.findById(id).get();
    }

    public List<OrderEntity> checkAllOrdersByStatus(OrderState status) {
        return orderRepository.showListByStatus(status);

    }

    public Optional<VehicleEntity> getVehicleById(long id) {
        Optional<VehicleEntity> vehicle = vehicleRepository.findById(id);
        if (vehicle.isPresent()) {
            return vehicle;
        }
        return Optional.empty();
    }

    public RentEntity createRent(RentEntity rent, Long sellerId, Long clientId, Long vehicleId) {
        VehicleEntity toRentVehicle = vehicleRepository.findById(vehicleId).get();
        if ((toRentVehicle.getRentable().equals(true)) && !(toRentVehicle.getSellType().equals(SellType.ORDERABLE))) {
            RentEntity newRent = new RentEntity();

            newRent.setSellerId(sellerRepository.findById(sellerId).get());
            newRent.setClientId(clientRepository.findById(clientId).get());
            newRent.setVehicleId(toRentVehicle);
            newRent.setStartingDate(rent.getStartingDate());
            newRent.setEndingDate(rent.getEndingDate());
            newRent.setDailyFee(rent.getDailyFee());
            newRent.setTotalFee(rent.getTotalFee());
            newRent.setIsPaid(rent.getIsPaid());
            toRentVehicle.setRentable(false);
            return newRent;
        }
        return null;
    }

    public RentEntity newRent(RentEntity rentEnt, Long clientId, Long vehicleId) {
        RentEntity rent = createRent(rentEnt, idLogin.getId(), clientId, vehicleId);
        if (rent != null) {
            return rentRepository.save(rent);
        } else {
            return null;
        }
    }


}
