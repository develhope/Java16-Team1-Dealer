package com.develhope.spring.customer;

import com.develhope.spring.customer.controllerResponses.*;
import com.develhope.spring.login.IdLogin;
import com.develhope.spring.rent.DTOs.rentCreationDTO;
import com.develhope.spring.order.DTOs.OrderClientDTO;
import com.develhope.spring.order.DTOs.PurchaseClientDTO;
import com.develhope.spring.order.OrderEntity;
import com.develhope.spring.order.OrderRepository;
import com.develhope.spring.order.OrderType;
import com.develhope.spring.rent.RentEntity;
import com.develhope.spring.rent.RentRepository;
import com.develhope.spring.rent.RentStatus;
import com.develhope.spring.shared.vehicle.VehicleRepository;
import com.develhope.spring.seller.SellerEntity;
import com.develhope.spring.shared.vehicle.SellType;
import com.develhope.spring.shared.vehicle.VehicleEntity;
import com.develhope.spring.seller.SellerRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;

@Transactional
@Service
public class CustomerService {
    @Autowired
    private IdLogin idLogin;
    @Autowired
    private CustomerRepository customerRepository;
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

    public ResponseEntity<String> deleteAccount() {
        customerRepository.delete(customerRepository.findById(idLogin.getId()).get());
        return ResponseEntity.status(200).body(errorMessagesClient.messageDeleteAccountOK());
    }

    public ResponseEntity<UpdateAccountResponse> updateAccount(CustomerEntity customerEntity) {
        if (customerEntity != null) {
            CustomerEntity client = customerRepository.findById(idLogin.getId()).get();
            if (customerEntity.getName() != null) {
                client.setName(customerEntity.getName());
            }
            if (customerEntity.getSurname() != null) {
                client.setSurname(customerEntity.getSurname());
            }
            if (customerEntity.getPhone() != null) {
                client.setPhone(customerEntity.getPhone());
            }
            if (customerEntity.getPsw() != null) {
                client.setPsw(customerEntity.getPsw());
            }
            UpdateAccountResponse updateAccountResponse = new UpdateAccountResponse(errorMessagesClient.messageUpdateAccountOK(), customerRepository.findById(idLogin.getId()).get());
            return ResponseEntity.status(607).body(updateAccountResponse);
        } else {
            UpdateAccountResponse updateAccountResponse = new UpdateAccountResponse(errorMessagesClient.noDetailsUpdateAccount(), customerRepository.findById(idLogin.getId()).get());
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


    public ResponseEntity<OrderResponse> createOrder(OrderClientDTO orderClientDTO) {
        OrderEntity newOrder = new OrderEntity();
        VehicleEntity vehicle;
        SellerEntity seller;
        CustomerEntity client = customerRepository.findById(idLogin.getId()).get();

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

    public ResponseEntity<ListOrderResponse> orderEntityList() {
        List<OrderEntity> listOrder = orderRepository.showListOrder(idLogin.getId());
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

    public ResponseEntity<PurchaseResponse> createPurchase(PurchaseClientDTO purchaseClientDTO) {
        OrderEntity newOrder = new OrderEntity();
        VehicleEntity vehicle;
        SellerEntity seller;
        CustomerEntity client = customerRepository.findById(idLogin.getId()).get();

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

    public ResponseEntity<ListPurchaseResponse> purchaseList() {
        List<OrderEntity> listOrder = orderRepository.showListPurchase(idLogin.getId());
        ;
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
            ListVehicleFilterResponse listVehicleFilterResponse = new ListVehicleFilterResponse(errorMessagesClient.noFilterApplied(), vehicleRepository.findAll());
            return ResponseEntity.status(610).body(listVehicleFilterResponse);
        } else {
            ListVehicleFilterResponse listVehicleFilterResponse = new ListVehicleFilterResponse(errorMessagesClient.listVehiclesFiltered(), vehicleRepository.showAllVehiclesFiltered(color, brand, model));
            return ResponseEntity.status(302).body(listVehicleFilterResponse);
        }

    }



    public RentEntity createRent(rentCreationDTO rentCreationDTO) {
        VehicleEntity vehicle = vehicleRepository.findById(rentCreationDTO.getIdVehicle()).get();
        if (vehicle.getRentable()) {
            RentEntity rent = new RentEntity();
            rent.setSellerId(sellerRepository.findById(rentCreationDTO.getIdSeller()).get());
            rent.setClientId(customerRepository.findById(rentCreationDTO.getIdClient()).get());
            rent.setVehicleId(vehicle);
            rent.setStartingDate(rentCreationDTO.getStartRent());
            rent.setEndingDate(rentCreationDTO.getEndRent());
            rent.setDailyFee(rentCreationDTO.getDailyFee());
            rent.setIsPaid(true);
            rent.setRentStatus(RentStatus.INPROGRESS);
            vehicleRepository.updateVehicleRentability(vehicle.getId());
            return rent;
        } else {
            return null;
        }
    }


    public List<RentEntity> showRents() {
        return rentRepository.showRentList(idLogin.getId());
    }

    public RentEntity newRent(rentCreationDTO rentCreationDTO) {
        RentEntity rent = createRent(rentCreationDTO);
        if (rent != null) {
            return rentRepository.save(rent);
        } else {
            return null;
        }

    }


    public void deleteRent(Long id) {
        rentRepository.customDeleteById(idLogin.getId(), id);
    }}
