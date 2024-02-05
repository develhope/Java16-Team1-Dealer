package com.develhope.spring.services;

import com.develhope.spring.dto.IdLogin;
import com.develhope.spring.entities.order.OrderEntity;
import com.develhope.spring.entities.order.OrderType;
import com.develhope.spring.entities.vehicle.SellType;
import com.develhope.spring.entities.vehicle.VehicleEntity;
import com.develhope.spring.repositories.*;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
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

    public OrderEntity createOrder(OrderEntity orderEntity, Long idSeller, Long idVehicle, Long idClient) {
        VehicleEntity vehicle = vehicleRepository.findById(idVehicle).get();
        if (vehicle.getSellType().equals(SellType.ORDERABLE)) {
            OrderEntity newOrder = new OrderEntity();
            newOrder.setOrderType(OrderType.ORDER);
            newOrder.setOrderState(orderEntity.getOrderState());
            newOrder.setAdvPayment(orderEntity.getAdvPayment());
            newOrder.setIsPaid(orderEntity.getIsPaid());
            newOrder.setVehicleId(vehicleRepository.findById(idVehicle).get());
            newOrder.setClientId(clientRepository.findById(idClient).get());
            newOrder.setSellerId(sellerRepository.findById(idSeller).get());
            return newOrder;
        } else {
            return null;
        }

    }
    public OrderEntity newOrder(OrderEntity orderEntity, Long idSeller, Long idVehicle) {
        if(idLogin.getType().equals("CLIENT")){
            OrderEntity order = createOrder(orderEntity, idSeller, idVehicle, idLogin.getId());
            if (order != null) {
                return orderRepository.save(order);
            } else {
                return null;
            }
        }
        return null;
    }

    public List<OrderEntity> orderEntityList() {
        if(idLogin.getType().equals("CLIENT")){
            return orderRepository.showListOrder(idLogin.getId());
        }else{
            return null;
        }
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
            newOrder.setVehicleId(vehicleRepository.findById(idVehicle).get());
            newOrder.setClientId(clientRepository.findById(idClient).get());
            newOrder.setSellerId(sellerRepository.findById(idSeller).get());
            return newOrder;
        } else {
            return null;
        }

    }

    public OrderEntity newPurchase(OrderEntity orderEntity, Long idSeller, Long idVehicle) {
        if(idLogin.getType().equals("CLIENT")){
            OrderEntity purchase = createPurchase(orderEntity, idSeller, idVehicle, idLogin.getId());
            if (purchase != null) {
                return orderRepository.save(purchase);
            } else {
                return null;
            }
        }
        return null;

    }

    public List<OrderEntity> purhcaseList() {
        if(idLogin.getType().equals("CLIENT")){
            return orderRepository.showListPurchase(idLogin.getId());
        }else{
            return null;
        }
    }









}
