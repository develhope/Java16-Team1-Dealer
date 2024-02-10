package com.develhope.spring.admin;

import com.develhope.spring.client.ClientRepository;
import com.develhope.spring.loginSignup.IdLogin;
import com.develhope.spring.order.OrderRepository;
import com.develhope.spring.rent.RentDto;
import com.develhope.spring.order.OrderEntity;
import com.develhope.spring.order.OrderType;
import com.develhope.spring.rent.*;
import com.develhope.spring.seller.SellerEntity;
import com.develhope.spring.vehicle.SellType;
import com.develhope.spring.vehicle.VehicleEntity;
import com.develhope.spring.rent.RentRepository;
import com.develhope.spring.seller.SellerRepository;
import com.develhope.spring.vehicle.VehicleRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.Objects;

@Service
@Transactional
public class AdminService {

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
    private AdminRepository adminRepository;
    @Autowired
    private RentRepository rentRepository;

    public OrderEntity newOrder(OrderEntity orderEntity, Long idSeller, Long idVehicle, Long idClient) {
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

    public OrderEntity createOrder(OrderEntity orderEntity, Long idSeller, Long idVehicle, Long idClient) {
        if (idLogin.getType().equals("ADMIN")) {
            return orderRepository.save(newOrder(orderEntity, idSeller, idVehicle, idClient));
        } else {
            return null;
        }
    }

    public OrderEntity updateStatusCancelled(Long idOrder) {
        orderRepository.updateStatusCancelledOrderWithId(idOrder);
        return orderRepository.findById(idOrder).get();
    }

    public OrderEntity updateOrder(OrderEntity orderEntity, Long idOrder) {
        if (idLogin.getType().equals("ADMIN")) {
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
        } else {
            return null;
        }

    }

    public OrderEntity newPurchase(OrderEntity orderEntity, Long idSeller, Long idVehicle, Long idClient) {
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
    public OrderEntity createPurchase(OrderEntity orderEntity, Long idSeller, Long idVehicle, Long idClient) {
        if (idLogin.getType().equals("ADMIN")) {
            return orderRepository.save(newPurchase(orderEntity, idSeller, idVehicle, idClient));
        } else {
            return null;
        }
    }

    public OrderEntity updateStatusCancelledPurchase(Long idOrder) {
        orderRepository.updateStatusCancelledPurchaseWithId(idOrder);
        return orderRepository.findById(idOrder).get();
    }

    public OrderEntity updatePurchase(OrderEntity orderEntity, Long idOrder) {
        if (idLogin.getType().equals("ADMIN")) {
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
        } else {
            return null;
        }

    }

    public RentEntity newRent(RentDto rentDto){
        VehicleEntity vehicle = vehicleRepository.findById(rentDto.getIdVehicle()).get();
        if(vehicle.getRentable()){
            RentEntity newRent = new RentEntity();
            newRent.setSellerId(sellerRepository.findById(rentDto.getIdSeller()).get());
            newRent.setClientId(clientRepository.findById(rentDto.getIdClient()).get());
            newRent.setVehicleId(vehicleRepository.findById(rentDto.getIdVehicle()).get());
            newRent.setStartingDate(rentDto.getStartRent());
            newRent.setEndingDate(rentDto.getEndRent());
            newRent.setDailyFee(rentDto.getDailyFee());
            newRent.setTotalFee(rentDto.getTotalFee());
            newRent.setIsPaid(true);
            vehicle.setRentable(false);
            return newRent;
        }else {
            return null;
        }
    }

    public RentEntity createRent(RentDto rentDto){
        return rentRepository.save(newRent(rentDto));
    }

    public RentEntity updateRent(Long idRent, RentDto rentDto){
        for(RentEntity r : rentRepository.findAll()){
            if(Objects.equals(r.getId(), idRent)) {
                if (rentDto.getIdSeller() != null) {
                    r.setSellerId(sellerRepository.findById(rentDto.getIdSeller()).get());
                }
                if (rentDto.getIdClient() != null) {
                    r.setClientId(clientRepository.findById(rentDto.getIdClient()).get());
                }
                if (rentDto.getIdVehicle() != null) {
                    r.setVehicleId(vehicleRepository.findById(rentDto.getIdVehicle()).get());
                }
                if (rentDto.getEndRent() != null) {
                    r.setEndingDate(rentDto.getEndRent());
                }
                if (rentDto.getDailyFee() != null) {
                    r.setDailyFee(rentDto.getDailyFee());
                }
                if (rentDto.getTotalFee() != null) {
                    r.setTotalFee(rentDto.getTotalFee());
                }
                return rentRepository.save(r);
            }
        }
        return null;
    }

    public RentEntity deleteRent(Long id){
        for(RentEntity r : rentRepository.findAll()){
            if(r.getId().equals(id)){
                rentRepository.deleteById(r.getId());
                return r;
            }
        }
        return null;
    }

    public String checkNumberOfSalesSeller(Long idSeller, LocalDate firstDate, LocalDate secondDate){
        for(SellerEntity s : sellerRepository.findAll()){
            if(s.getId()==idSeller){
                return "Number of purchase " + orderRepository.checkNumberOfSalesSeller(s.getId(),firstDate,secondDate);
            }
        }
        return null;
    }


}