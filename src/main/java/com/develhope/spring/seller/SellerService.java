package com.develhope.spring.seller;

import com.develhope.spring.client.ClientRepository;
import com.develhope.spring.loginSignup.IdLogin;
import com.develhope.spring.order.*;
import com.develhope.spring.rent.*;
import com.develhope.spring.seller.sellerControllerResponse.ErrorMessageSeller;
import com.develhope.spring.seller.sellerControllerResponse.GetVehicleBySellerResponse;
import com.develhope.spring.vehicle.*;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
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

    @Autowired
    private ErrorMessageSeller errorMessageSeller;


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

    public ResponseEntity<GetVehicleBySellerResponse> getVehicleById(long id) {
        Optional<VehicleEntity> vehicle = vehicleRepository.findById(id);
        if (vehicle.isPresent()) {
            GetVehicleBySellerResponse okResponse = new GetVehicleBySellerResponse(errorMessageSeller.showFoundVehicle(id), vehicle.get());
            return ResponseEntity.status(200).body(okResponse);
        }
        GetVehicleBySellerResponse notFoundResponse = new GetVehicleBySellerResponse(errorMessageSeller.vehicleNotFound(id), null);
        return ResponseEntity.status(404).body(notFoundResponse);
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
            newRent.setRentStatus(RentStatus.INPROGRESS);
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

    public RentEntity updateRent(RentEntity updatedRent, Long rentId) {
        Optional<RentEntity> toUpdateRent = rentRepository.findById(rentId);
        if (toUpdateRent.isPresent()) {

            toUpdateRent.get().setStartingDate(updatedRent.getStartingDate());
            toUpdateRent.get().setEndingDate(updatedRent.getEndingDate());
            toUpdateRent.get().setDailyFee(updatedRent.getDailyFee());
            toUpdateRent.get().setTotalFee(updatedRent.getTotalFee());
            toUpdateRent.get().setIsPaid(updatedRent.getIsPaid());

            return rentRepository.saveAndFlush(toUpdateRent.get());
        } else {
            return null;
        }
    }


    public Boolean deleteRent(Long rentToDeleteId) {
        Optional<RentEntity> toDeleteRent = rentRepository.findById(rentToDeleteId);
        LocalDateTime actualDate = LocalDateTime.now();
        if (toDeleteRent.isPresent() && (actualDate.isBefore(toDeleteRent.get().getEndingDate()))) {
            toDeleteRent.get().setRentStatus(RentStatus.DELETED);
            rentRepository.save(toDeleteRent.get());
            return true;
        } else {
            return false;
        }
    }

    public List<VehicleEntity> getAllVehicles() {
        return vehicleRepository.findAll();
    }

    public List<VehicleEntity> getAllVehiclesRfd() {
        return vehicleRepository.showAllVehiclesRfd();
    }

    public List<VehicleEntity> getAllRentableVehicles() {
        return vehicleRepository.showAllRentableVehicles();
    }

}
