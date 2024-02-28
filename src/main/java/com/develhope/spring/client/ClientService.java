package com.develhope.spring.client;

import com.develhope.spring.client.clientControllerResponse.*;
import com.develhope.spring.order.*;
import com.develhope.spring.rent.*;
import com.develhope.spring.order.dto.*;
import com.develhope.spring.seller.*;
import com.develhope.spring.user.UserEntity;
import com.develhope.spring.vehicle.*;
import jakarta.transaction.Transactional;
import org.apache.catalina.User;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Transactional
@Service
public class ClientService {
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
    private ErrorMessagesClient errorMessagesClient;

    public ResponseEntity<String> deleteAccount(UserEntity user) {
        clientRepository.delete(clientRepository.findById(user.getId()).get()); //DONE aggiungere UserEntity e prendere id
        return ResponseEntity.status(200).body(errorMessagesClient.messageDeleteAccountOK());
    }

    public ResponseEntity<UpdateAccountResponse> updateAccount(UserEntity user, ClientEntity clientEntity) {  //DONE aggiungere UserEntity e prendere id e nel metodo @AuthenticationPrincipal UserEntity user
        if (clientEntity != null) {
            ClientEntity client = clientRepository.findById(user.getId()).get();
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
            UpdateAccountResponse updateAccountResponse = new UpdateAccountResponse(errorMessagesClient.messageUpdateAccountOK(), clientRepository.findById(user.getId()).get());
            return ResponseEntity.status(607).body(updateAccountResponse);
        } else {
            UpdateAccountResponse updateAccountResponse = new UpdateAccountResponse(errorMessagesClient.noDetailsUpdateAccount(), clientRepository.findById(user.getId()).get());
            return ResponseEntity.status(406).body(updateAccountResponse);
        }


    }

    public ResponseEntity<ShowVehicleIDResponse> showVehicleID(Long idVehicle) {
        VehicleEntity vehicle = new VehicleEntity();
        if (vehicleRepository.existsById(idVehicle)) {
            vehicle = vehicleRepository.findById(idVehicle).get();
            ShowVehicleIDResponse showVehicleIDResponse = new ShowVehicleIDResponse(errorMessagesClient.vehicleFound(), vehicle);
            return ResponseEntity.status(302).body(showVehicleIDResponse);
        } else {
            ShowVehicleIDResponse showVehicleIDResponse = new ShowVehicleIDResponse(errorMessagesClient.vehicleNotFound(), vehicle);
            return ResponseEntity.status(404).body(showVehicleIDResponse);
        }
    }


    public ResponseEntity<OrderResponse> createOrder(UserEntity user, OrderClientDTO orderClientDTO) {
        OrderEntity newOrder = new OrderEntity();
        VehicleEntity vehicle;
        SellerEntity seller;
        ClientEntity client = clientRepository.findById(user.getId()).get();  //DONE aggiungere UserEntity e prendere id e nel metodo @AuthenticationPrincipal UserEntity user

        if (sellerRepository.existsById(orderClientDTO.getIdSeller())) {
            seller = sellerRepository.findById(orderClientDTO.getIdSeller()).get();
        } else {
            OrderResponse orderResponse = new OrderResponse(errorMessagesClient.sellerNotFound(orderClientDTO.getIdSeller()), newOrder);
            return ResponseEntity.status(601).body(orderResponse);
        }

        if (vehicleRepository.existsById(orderClientDTO.getIdVehicle())) {
            vehicle = vehicleRepository.findById(orderClientDTO.getIdVehicle()).get();
            if (vehicle.getSellType().equals(SellType.ORDERABLE)) {
                newOrder.setOrderType(OrderType.ORDER);
                newOrder.setDatePurchase(LocalDateTime.now());
                newOrder.setOrderState(orderClientDTO.getOrderState());
                newOrder.setAdvPayment(orderClientDTO.getAdvPayment());
                newOrder.setIsPaid(orderClientDTO.getIsPaid());
                newOrder.setVehicleId(vehicle);
                newOrder.setClientId(client);
                newOrder.setSellerId(seller);
                orderRepository.save(newOrder);
                OrderResponse orderResponse = new OrderResponse(errorMessagesClient.orderCreated(), newOrder);
                return ResponseEntity.status(201).body(orderResponse);
            } else {
                OrderResponse orderResponse = new OrderResponse(errorMessagesClient.vehicleNotOrderable(orderClientDTO.getIdVehicle()), newOrder);
                return ResponseEntity.status(602).body(orderResponse);
            }
        } else {
            OrderResponse orderResponse = new OrderResponse(errorMessagesClient.vehicleNotExist(orderClientDTO.getIdVehicle()), newOrder);
            return ResponseEntity.status(600).body(orderResponse);
        }
    }

    public ResponseEntity<ListOrderResponse> orderEntityList(UserEntity user) {
        List<OrderEntity> listOrder = orderRepository.showListOrder(user.getId());
        if (listOrder.size() > 0) {
            ListOrderResponse listOrderResponse = new ListOrderResponse(errorMessagesClient.ordersFound(), listOrder);
            return ResponseEntity.status(200).body(listOrderResponse);
        } else {
            ListOrderResponse listOrderResponse = new ListOrderResponse(errorMessagesClient.ordersNotFound(), Arrays.asList());
            return ResponseEntity.status(404).body(listOrderResponse);
        }
    }

    public ResponseEntity<StatusCancelledResponse> updateStatusCancelled(Long idOrder) {
        OrderEntity order = new OrderEntity();
        if (!orderRepository.existsById(idOrder)) {
            StatusCancelledResponse statusCancelledResponse = new StatusCancelledResponse(errorMessagesClient.orderNotExist(idOrder), order);
            return ResponseEntity.status(404).body(statusCancelledResponse);
        } else {
            orderRepository.updateStatusCancelledOrderWithId(idOrder);
            order = orderRepository.findById(idOrder).get();
            StatusCancelledResponse statusCancelledResponse = new StatusCancelledResponse(errorMessagesClient.orderStatusCancelledOk(idOrder), order);
            return ResponseEntity.status(200).body(statusCancelledResponse);
        }
    }

    public ResponseEntity<PurchaseResponse> createPurchase(UserEntity user, PurchaseClientDTO purchaseClientDTO) {
        OrderEntity newOrder = new OrderEntity();
        VehicleEntity vehicle;
        SellerEntity seller;
        ClientEntity client = clientRepository.findById(user.getId()).get();  //DONE aggiungere UserEntity e prendere id e nel metodo @AuthenticationPrincipal UserEntity user

        if (sellerRepository.existsById(purchaseClientDTO.getIdSeller())) {
            seller = sellerRepository.findById(purchaseClientDTO.getIdSeller()).get();
        } else {
            PurchaseResponse purchaseResponse = new PurchaseResponse(errorMessagesClient.sellerNotFound(purchaseClientDTO.getIdSeller()), newOrder);
            return ResponseEntity.status(601).body(purchaseResponse);
        }

        if (vehicleRepository.existsById(purchaseClientDTO.getIdVehicle())) {
            vehicle = vehicleRepository.findById(purchaseClientDTO.getIdVehicle()).get();
            if (vehicle.getSellType().equals(SellType.RFD)) {
                newOrder.setOrderType(OrderType.PURCHASE);
                newOrder.setDatePurchase(LocalDateTime.now());
                newOrder.setOrderState(purchaseClientDTO.getOrderState());
                newOrder.setAdvPayment(purchaseClientDTO.getAdvPayment());
                newOrder.setIsPaid(purchaseClientDTO.getIsPaid());
                newOrder.setVehicleId(vehicle);
                newOrder.setClientId(client);
                newOrder.setSellerId(seller);
                orderRepository.save(newOrder);
                PurchaseResponse purchaseResponse = new PurchaseResponse(errorMessagesClient.purchaseCreated(), newOrder);
                return ResponseEntity.status(201).body(purchaseResponse);
            } else {
                PurchaseResponse purchaseResponse = new PurchaseResponse(errorMessagesClient.vehicleNotPurchasable(purchaseClientDTO.getIdVehicle()), newOrder);
                return ResponseEntity.status(602).body(purchaseResponse);
            }
        } else {
            PurchaseResponse purchaseResponse = new PurchaseResponse(errorMessagesClient.vehicleNotExist(purchaseClientDTO.getIdVehicle()), newOrder);
            return ResponseEntity.status(600).body(purchaseResponse);
        }
    }

    public ResponseEntity<ListPurchaseResponse> purchaseList(UserEntity user) {
        List<OrderEntity> listOrder = orderRepository.showListPurchase(user.getId()); //DONE aggiungere UserEntity e prendere id e nel metodo @AuthenticationPrincipal UserEntity user

        if (listOrder.size() > 0) {
            ListPurchaseResponse listPurchaseResponse = new ListPurchaseResponse(errorMessagesClient.purchasesFound(), listOrder);
            return ResponseEntity.status(200).body(listPurchaseResponse);
        } else {
            ListPurchaseResponse listPurchaseResponse = new ListPurchaseResponse(errorMessagesClient.purchasesNotFound(), Arrays.asList());
            return ResponseEntity.status(404).body(listPurchaseResponse);
        }
    }

    public ResponseEntity<ListVehicleFilterResponse> filterFindVehicleByRangePrice(BigDecimal minPrice, BigDecimal maxPrice) {
        BigDecimal[] price = {minPrice, maxPrice};
        Arrays.sort(price);
        List<VehicleEntity> vehicleListFilterPrice = vehicleRepository.showAllVehiclesByRangePrice(price[0], price[1]);
        if (vehicleListFilterPrice.size() > 0) {
            ListVehicleFilterResponse listVehicleFilterResponse = new ListVehicleFilterResponse(errorMessagesClient.vehiclesFound(), vehicleListFilterPrice);
            return ResponseEntity.status(302).body(listVehicleFilterResponse);
        } else {
            ListVehicleFilterResponse listVehicleFilterResponse = new ListVehicleFilterResponse(errorMessagesClient.vehiclesNotFoundByRangePrice(), Arrays.asList());
            return ResponseEntity.status(611).body(listVehicleFilterResponse);
        }
    }

    public ResponseEntity<ListVehicleFilterResponse> showAllVehiclesFiltered(String color, String brand, String model) {
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
            ListVehicleFilterResponse listVehicleFilterResponse = new ListVehicleFilterResponse(errorMessagesClient.noFilterApplied(), vehicleRepository.findAll());
            return ResponseEntity.status(610).body(listVehicleFilterResponse);
        } else {
            ListVehicleFilterResponse listVehicleFilterResponse = new ListVehicleFilterResponse(errorMessagesClient.listVehiclesFiltered(), vehicleRepository.showAllVehiclesFiltered(color, brand, model));
            return ResponseEntity.status(302).body(listVehicleFilterResponse);
        }

    }


    public RentEntity createRent(UserEntity user, RentDtoInput rentDtoInput, VehicleEntity vehicle, SellerEntity seller) {
        RentEntity rent = new RentEntity();
        rent.setSellerId(seller);
        rent.setClientId(clientRepository.findById(user.getId()).get()); //DONE aggiungere UserEntity e prendere id e nel metodo @AuthenticationPrincipal UserEntity user
        rent.setVehicleId(vehicle);
        rent.setStartingDate(rentDtoInput.getStartRent());
        rent.setEndingDate(rentDtoInput.getEndRent());
        rent.setDailyFee(rentDtoInput.getDailyFee());
        rent.setIsPaid(true);
        rent.setRentStatus(RentStatus.INPROGRESS);
        vehicleRepository.updateVehicleRentability(vehicle.getId());
        return rent;
    }


    public ResponseEntity<ShowRentListClientResponse> showRents(UserEntity user) {
        if (!rentRepository.showRentList(user.getId()).isEmpty()) {  //DONE aggiungere UserEntity e prendere id e nel metodo @AuthenticationPrincipal UserEntity user
            List<RentEntity> rentEntities = rentRepository.showRentList(user.getId());
            List<RentDtoOutput> rentDtos = rentEntitiesConverter(rentEntities);
            ShowRentListClientResponse showRentListClientResponse = new ShowRentListClientResponse(errorMessagesClient.rentListClientOK(rentDtos.size()), rentDtos);
            return ResponseEntity.status(200).body(showRentListClientResponse);
        } else {
            ShowRentListClientResponse showRentListClientResponse = new ShowRentListClientResponse(errorMessagesClient.rentListClientEmpty(), Arrays.asList());
            return ResponseEntity.status(404).body(showRentListClientResponse);
        }
    }

    public ResponseEntity<NewRentResponse> newRent(UserEntity user, RentDtoInput rentDtoInput) {
        SellerEntity seller;

        if (sellerRepository.existsById(rentDtoInput.getIdSeller())) {
            seller = sellerRepository.findById(rentDtoInput.getIdSeller()).get();
        } else {
            NewRentResponse newRentResponse = new NewRentResponse(errorMessagesClient.sellerNotFound(rentDtoInput.getIdSeller()), new RentDtoOutput());
            return ResponseEntity.status(601).body(newRentResponse);
        }

        if (vehicleRepository.existsById(rentDtoInput.getIdVehicle())) {
            VehicleEntity vehicle = vehicleRepository.findById(rentDtoInput.getIdVehicle()).get();

            if (vehicle.getRentable()) {
                RentEntity rentEntity = rentRepository.save(createRent(user, rentDtoInput, vehicle, seller));
                RentDtoOutput newRent = rentEntityConverter(rentEntity);

                NewRentResponse newRentResponse = new NewRentResponse(errorMessagesClient.rentCreated(), newRent);
                return ResponseEntity.status(201).body(newRentResponse);
            } else {
                NewRentResponse newRentResponse = new NewRentResponse(errorMessagesClient.vehicleNotRentable(vehicle.getId()), new RentDtoOutput());
                return ResponseEntity.status(602).body(newRentResponse);
            }
        } else {
            NewRentResponse newRentResponse = new NewRentResponse(errorMessagesClient.vehicleNotExist(rentDtoInput.getIdVehicle()), new RentDtoOutput());
            return ResponseEntity.status(600).body(newRentResponse);
        }
    }


    public ResponseEntity<RentDeletionClientResponse> deleteRent(UserEntity user, Long id) {
        if (rentRepository.existsById(id)) {
            vehicleRepository.resetVehicleRentability(id);
            rentRepository.customDeleteById(user.getId(), id);  //DONE aggiungere UserEntity e prendere id e nel metodo @AuthenticationPrincipal UserEntity user
            RentDeletionClientResponse rentDeletionClientResponse = new RentDeletionClientResponse(errorMessagesClient.messageDeleteRentOK());
            return ResponseEntity.status(200).body(rentDeletionClientResponse);
        } else {
            RentDeletionClientResponse rentDeletionClientResponse = new RentDeletionClientResponse(errorMessagesClient.rentNotFound(id));
            return ResponseEntity.status(404).body(rentDeletionClientResponse);
        }
    }

    public RentDtoOutput rentEntityConverter(RentEntity entity) {
        ModelMapper modelMapper = new ModelMapper();
        return modelMapper.map(entity, RentDtoOutput.class);
    }

    public List<RentDtoOutput> rentEntitiesConverter(List<RentEntity> entityList) {
        ArrayList<RentDtoOutput> rentDtos = new ArrayList<>();
        for (int i = 0; i < entityList.size(); i++) {
            rentDtos.add(rentEntityConverter(entityList.get(i)));
        }
        return rentDtos;

    }

}
