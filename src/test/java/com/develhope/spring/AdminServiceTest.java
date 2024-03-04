package com.develhope.spring;

import com.develhope.spring.admin.AdminRepository;
import com.develhope.spring.admin.AdminService;
import com.develhope.spring.admin.ShowSellerRevenueOverTimePeriod;
import com.develhope.spring.admin.adminControllerResponse.ShowSellerVehiclesSoldOverTimePeriod;
import com.develhope.spring.admin.adminControllerResponse.UpdateStatusCancelledPurchase;
import com.develhope.spring.client.ClientEntity;
import com.develhope.spring.client.ClientRepository;
import com.develhope.spring.order.OrderEntity;
import com.develhope.spring.order.OrderRepository;
import com.develhope.spring.order.OrderState;
import com.develhope.spring.order.OrderType;
import com.develhope.spring.seller.SellerEntity;
import com.develhope.spring.seller.SellerRepository;
import com.develhope.spring.user.UserRepository;
import com.develhope.spring.user.UserType;
import com.develhope.spring.vehicle.GearType;
import com.develhope.spring.vehicle.SellType;
import com.develhope.spring.vehicle.VehicleEntity;
import com.develhope.spring.vehicle.VehicleRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.TestPropertySource;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Objects;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@TestPropertySource(value = {"classpath:application-test.properties"})
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
public class AdminServiceTest {

    @Autowired
    private AdminService adminService;

    @Autowired
    private AdminRepository adminRepository;

    @Autowired
    private VehicleRepository vehicleRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ClientRepository clientRepository;

    @Autowired
    private SellerRepository sellerRepository;

    @Autowired
    private OrderRepository orderRepository;

    @Test
    void contextLoads() {
    }

    private VehicleEntity createVehicleOrderable() {
        VehicleEntity vehicle = new VehicleEntity();
        vehicle.setBrand("Fiat");
        vehicle.setModel("500L");
        vehicle.setEngineCapacity(1300);
        vehicle.setColour("Black");
        vehicle.setHp(90);
        vehicle.setGearType(GearType.MANUAL);
        vehicle.setRegisterYear(LocalDate.of(2022, 01, 01));
        vehicle.setFuelType("Diesel");
        vehicle.setPrice(BigDecimal.valueOf(30000));
        vehicle.setPriceDscnt(5);
        vehicle.setAccessories("full optional");
        vehicle.setRentable(true);
        vehicle.setSellType(SellType.ORDERABLE);
        return vehicle;
    }

    private VehicleEntity createVehicleRFD() {
        VehicleEntity vehicle = new VehicleEntity();
        vehicle.setBrand("Peguet");
        vehicle.setModel("106");
        vehicle.setEngineCapacity(1800);
        vehicle.setColour("Blue");
        vehicle.setHp(110);
        vehicle.setGearType(GearType.MANUAL);
        vehicle.setRegisterYear(LocalDate.of(2022, 01, 01));
        vehicle.setFuelType("Gasoline");
        vehicle.setPrice(BigDecimal.valueOf(35000));
        vehicle.setPriceDscnt(8);
        vehicle.setAccessories("full optional");
        vehicle.setRentable(false);
        vehicle.setSellType(SellType.RFD);
        return vehicle;
    }

    private VehicleEntity createVehicleUsed() {
        VehicleEntity vehicle = new VehicleEntity();
        vehicle.setBrand("Fiat");
        vehicle.setModel("500L");
        vehicle.setEngineCapacity(1300);
        vehicle.setColour("Black");
        vehicle.setHp(90);
        vehicle.setGearType(GearType.MANUAL);
        vehicle.setRegisterYear(LocalDate.of(2022, 01, 01));
        vehicle.setFuelType("Diesel");
        vehicle.setPrice(BigDecimal.valueOf(30000));
        vehicle.setPriceDscnt(5);
        vehicle.setAccessories("full optional");
        vehicle.setRentable(true);
        vehicle.setSellType(SellType.USED);
        return vehicle;
    }

    private ClientEntity createClient() {
        ClientEntity client = new ClientEntity();
        client.setName("Anto");
        client.setSurname("Troiano");
        client.setEmail("anto@gmail.com");
        client.setPsw("anto");
        client.setType(UserType.CLIENT);
        return client;
    }

    private SellerEntity createSeller() {
        SellerEntity seller = new SellerEntity();
        seller.setName("Pino");
        seller.setSurname("Franco");
        seller.setEmail("pin8@gmail.com");
        seller.setPsw("pino");
        seller.setType(UserType.SELLER);
        return seller;

    }

    private OrderEntity createOrderTest() {
        createVehicleOrderable();
        createSeller();
        createClient();
        vehicleRepository.save(createVehicleOrderable());
        sellerRepository.save(createSeller());
        clientRepository.save(createClient());

        VehicleEntity vehicle = vehicleRepository.findById(1L).get();
        SellerEntity seller = sellerRepository.findById(1L).get();
        ClientEntity client = clientRepository.findById(2L).get();

        OrderEntity newOrder = new OrderEntity();
        newOrder.setOrderType(OrderType.ORDER);
        newOrder.setOrderState(OrderState.DELIVERED);
        newOrder.setAdvPayment(BigDecimal.valueOf(10000));
        newOrder.setIsPaid(true);
        newOrder.setVehicleId(vehicle);
        newOrder.setClientId(client);
        newOrder.setSellerId(seller);

        return newOrder;
    }

    private OrderEntity purchase(){
        createVehicleRFD();
        createSeller();
        createClient();
        vehicleRepository.save(createVehicleRFD());
        sellerRepository.save(createSeller());
        clientRepository.save(createClient());

        VehicleEntity vehicle = vehicleRepository.findById(1L).get();
        SellerEntity seller = sellerRepository.findById(1L).get();
        ClientEntity clientEntity = clientRepository.findById(2L).get();


        OrderEntity newOrder = new OrderEntity();
        newOrder.setOrderType(OrderType.PURCHASE);
        newOrder.setOrderState(OrderState.SHIPPED);
        newOrder.setAdvPayment(BigDecimal.valueOf(10000));
        newOrder.setIsPaid(true);
        newOrder.setVehicleId(vehicle);
        newOrder.setClientId(clientEntity);
        newOrder.setSellerId(seller);

        return  newOrder;
    }

    public OrderEntity createOrderWithArguments(SellerEntity seller, ClientEntity client, VehicleEntity vehicle) {
        OrderEntity newOrder = new OrderEntity();
        newOrder.setSellerId(seller);
        newOrder.setClientId(client);
        newOrder.setVehicleId(vehicle);
        newOrder.setAdvPayment(BigDecimal.valueOf(200));
        newOrder.setIsPaid(Boolean.TRUE);
        newOrder.setOrderState(OrderState.DELIVERED);
        newOrder.setDatePurchase(LocalDateTime.now());
        newOrder.setOrderType(OrderType.ORDER);
        return newOrder;
    }


    @Test
    void newOrder() {
        createVehicleOrderable();
        createSeller();
        createClient();
        vehicleRepository.save(createVehicleOrderable());
        sellerRepository.save(createSeller());
        clientRepository.save(createClient());

        VehicleEntity vehicle = vehicleRepository.findById(1L).get();
        SellerEntity seller = sellerRepository.findById(1L).get();
        ClientEntity clientEntity = clientRepository.findById(2L).get();


        OrderEntity newOrder = new OrderEntity();
        newOrder.setOrderType(OrderType.ORDER);
        newOrder.setOrderState(OrderState.DELIVERED);
        newOrder.setAdvPayment(BigDecimal.valueOf(10000));
        newOrder.setIsPaid(true);
        newOrder.setVehicleId(vehicle);
        newOrder.setClientId(clientEntity);
        newOrder.setSellerId(seller);

        assertThat(adminService.newOrder(newOrder, seller.getId(), vehicle.getId(), clientEntity.getId())).isNotNull();
    }

    @Test
    void newOrderWithNotOrderableVehicle() {
        createClient();
        createSeller();
        createVehicleUsed();
        vehicleRepository.save(createVehicleUsed());
        sellerRepository.save(createSeller());
        clientRepository.save(createClient());

        VehicleEntity vehicle = vehicleRepository.findById(1L).get();
        SellerEntity seller = sellerRepository.findById(1L).get();
        ClientEntity client = clientRepository.findById(2L).get();

        OrderEntity newOrder = new OrderEntity();
        newOrder.setOrderType(OrderType.ORDER);
        newOrder.setOrderState(OrderState.DELIVERED);
        newOrder.setAdvPayment(BigDecimal.valueOf(10000));
        newOrder.setIsPaid(true);
        newOrder.setVehicleId(vehicle);
        newOrder.setClientId(client);
        newOrder.setSellerId(seller);

        OrderEntity order = adminService.newOrder(newOrder, seller.getId(), vehicle.getId(), client.getId());

        assertNull(order);
    }

    @Test
    void createOrder() {
        createClient();
        createSeller();
        createVehicleOrderable();
        vehicleRepository.save(createVehicleOrderable());
        sellerRepository.save(createSeller());
        clientRepository.save(createClient());

        VehicleEntity vehicle = vehicleRepository.findById(1L).get();
        SellerEntity seller = sellerRepository.findById(1L).get();
        ClientEntity client = clientRepository.findById(2L).get();

        OrderEntity newOrder = new OrderEntity();
        newOrder.setOrderType(OrderType.ORDER);
        newOrder.setOrderState(OrderState.DELIVERED);
        newOrder.setAdvPayment(BigDecimal.valueOf(10000));
        newOrder.setIsPaid(true);
        newOrder.setVehicleId(vehicle);
        newOrder.setClientId(client);
        newOrder.setSellerId(seller);

        ResponseEntity<String> orderResponse = adminService.createOrder(newOrder, seller.getId(), vehicle.getId(), client.getId());

        assertThat(orderResponse.getStatusCode().value()).isEqualTo(200);
        assertThat(orderResponse.getBody()).isEqualTo("Order successfully created");
    }

    @Test
    void updateOrder() {
        OrderEntity order = createOrderTest();
        orderRepository.save(order);
        order.setAdvPayment(BigDecimal.TEN);

        ResponseEntity<String> update = adminService.updateOrder(order, order.getId());

        assertThat(update.getStatusCode().value()).isEqualTo(200);
        assertThat(update.getBody()).isEqualTo("Order successfully update");

    }

    @Test
    void newPurchase() {
        createVehicleRFD();
        createSeller();
        createClient();
        vehicleRepository.save(createVehicleRFD());
        sellerRepository.save(createSeller());
        clientRepository.save(createClient());

        VehicleEntity vehicle = vehicleRepository.findById(1L).get();
        SellerEntity seller = sellerRepository.findById(1L).get();
        ClientEntity clientEntity = clientRepository.findById(2L).get();


        OrderEntity newOrder = new OrderEntity();
        newOrder.setOrderType(OrderType.PURCHASE);
        newOrder.setOrderState(OrderState.SHIPPED);
        newOrder.setAdvPayment(BigDecimal.valueOf(10000));
        newOrder.setIsPaid(true);
        newOrder.setVehicleId(vehicle);
        newOrder.setClientId(clientEntity);
        newOrder.setSellerId(seller);

        assertThat(adminService.newPurchase(newOrder, seller.getId(), vehicle.getId(), clientEntity.getId())).isNotNull();
    }

    @Test
    void createPurchase(){
        createVehicleRFD();
        createSeller();
        createClient();
        vehicleRepository.save(createVehicleRFD());
        sellerRepository.save(createSeller());
        clientRepository.save(createClient());

        VehicleEntity vehicle = vehicleRepository.findById(1L).get();
        SellerEntity seller = sellerRepository.findById(1L).get();
        ClientEntity clientEntity = clientRepository.findById(2L).get();


        OrderEntity newOrder = new OrderEntity();
        newOrder.setOrderType(OrderType.PURCHASE);
        newOrder.setOrderState(OrderState.SHIPPED);
        newOrder.setAdvPayment(BigDecimal.valueOf(10000));
        newOrder.setIsPaid(true);
        newOrder.setVehicleId(vehicle);
        newOrder.setClientId(clientEntity);
        newOrder.setSellerId(seller);

        ResponseEntity<String> response = adminService.createPurchase(newOrder, seller.getId(), vehicle.getId(), clientEntity.getId());

        assertThat(response.getStatusCode().value()).isEqualTo(200);
        assertThat(response.getBody()).isEqualTo("Order successfully purchased");
    }

    @Test
    void updateStatusCancelledPurchase(){
        OrderEntity purchase = purchase();
        orderRepository.save(purchase);

        ResponseEntity<UpdateStatusCancelledPurchase> updateStatusCancelledPurchase = adminService.updateStatusCancelledPurchase(purchase.getId());

        assertThat(updateStatusCancelledPurchase.getStatusCode().value()).isEqualTo(200);
        assertThat(Objects.requireNonNull(updateStatusCancelledPurchase.getBody()).getMessage()).isEqualTo("The order " + purchase.getId() + " is successfully cancelled");

    }


    @Test
    void showSellerRevenueOverTimePeriodOkResponse() {
        SellerEntity testSeller = sellerRepository.saveAndFlush(createSeller());
        VehicleEntity testVehicleOne = vehicleRepository.save(createVehicleOrderable());
        VehicleEntity testVehicleTwo = vehicleRepository.save(createVehicleOrderable());
        ClientEntity testClient = clientRepository.save(createClient());
        LocalDateTime dateOne = LocalDateTime.now().minusMinutes(1);
        OrderEntity testOrderOne = orderRepository.save(createOrderWithArguments(testSeller,testClient,testVehicleOne));
        OrderEntity testOrderTwo = orderRepository.save(createOrderWithArguments(testSeller,testClient,testVehicleTwo));
        LocalDateTime dateTwo = LocalDateTime.now().plusMinutes(1);

        ResponseEntity<ShowSellerRevenueOverTimePeriod> response = adminService.showSellerRevenueOverTimePeriod(testSeller.getId(),dateOne,dateTwo);
        response.getBody().setRevenue(response.getBody().getRevenue().setScale(2));

        assertEquals(response.getStatusCode(), HttpStatusCode.valueOf(200));
        assertEquals(BigDecimal.valueOf(59990).setScale(2),response.getBody().getRevenue());
        assertEquals("The seller identified by id " + testSeller.getId() + " has earned a revenue of " + BigDecimal.valueOf(59990).setScale(2) + " between " + dateOne + " and " + dateTwo,response.getBody().getMessage());

    }

    @Test
    void showSellerRevenueOverTimePeriodMissingSeller() {
        SellerEntity testSeller = sellerRepository.saveAndFlush(createSeller());
        VehicleEntity testVehicleOne = vehicleRepository.save(createVehicleOrderable());
        VehicleEntity testVehicleTwo = vehicleRepository.save(createVehicleOrderable());
        ClientEntity testClient = clientRepository.save(createClient());
        LocalDateTime dateOne = LocalDateTime.now().minusMinutes(1);
        OrderEntity testOrderOne = orderRepository.save(createOrderWithArguments(testSeller,testClient,testVehicleOne));
        OrderEntity testOrderTwo = orderRepository.save(createOrderWithArguments(testSeller,testClient,testVehicleTwo));
        LocalDateTime dateTwo = LocalDateTime.now().plusMinutes(1);

        ResponseEntity<ShowSellerRevenueOverTimePeriod> response = adminService.showSellerRevenueOverTimePeriod(Long.valueOf(-1),dateOne,dateTwo);
        response.getBody().setRevenue(response.getBody().getRevenue().setScale(2));

        assertEquals(HttpStatusCode.valueOf(404),response.getStatusCode());
        assertEquals(BigDecimal.valueOf(0).setScale(2),response.getBody().getRevenue());
        assertEquals("Seller with id " + Long.valueOf(-1) + " does not exist",response.getBody().getMessage());

    }

    @Test
    void showSellerRevenueOverTimePeriodInvalidDate() {
        SellerEntity testSeller = sellerRepository.saveAndFlush(createSeller());
        VehicleEntity testVehicleOne = vehicleRepository.save(createVehicleOrderable());
        VehicleEntity testVehicleTwo = vehicleRepository.save(createVehicleOrderable());
        ClientEntity testClient = clientRepository.save(createClient());
        LocalDateTime dateOne = null;
        OrderEntity testOrderOne = orderRepository.save(createOrderWithArguments(testSeller,testClient,testVehicleOne));
        OrderEntity testOrderTwo = orderRepository.save(createOrderWithArguments(testSeller,testClient,testVehicleTwo));
        LocalDateTime dateTwo = null;

        ResponseEntity<ShowSellerRevenueOverTimePeriod> response = adminService.showSellerRevenueOverTimePeriod(testSeller.getId(),dateOne,dateTwo);
        response.getBody().setRevenue(response.getBody().getRevenue().setScale(2));

        assertEquals(response.getStatusCode(), HttpStatusCode.valueOf(400));
        assertEquals(BigDecimal.valueOf(0).setScale(2),response.getBody().getRevenue());
        assertEquals("You did not provide two valid dates for the search.\n" +
                        "Please make sure that your input matches the following format: yyyy-MM-ddTHH:mm:ss\n" +
                        "Example: 2024-02-20T14:30:00"
                ,response.getBody().getMessage());

    }


    @Test
    void showSellerVehiclesSoldOverTimePeriodOkResponse() {
        SellerEntity testSeller = sellerRepository.saveAndFlush(createSeller());
        VehicleEntity testVehicleOne = vehicleRepository.save(createVehicleOrderable());
        VehicleEntity testVehicleTwo = vehicleRepository.save(createVehicleOrderable());
        ClientEntity testClient = clientRepository.save(createClient());
        LocalDateTime dateOne = LocalDateTime.now().minusMinutes(1);
        OrderEntity testOrderOne = orderRepository.save(createOrderWithArguments(testSeller,testClient,testVehicleOne));
        OrderEntity testOrderTwo = orderRepository.save(createOrderWithArguments(testSeller,testClient,testVehicleTwo));
        LocalDateTime dateTwo = LocalDateTime.now().plusMinutes(1);

        ResponseEntity<ShowSellerVehiclesSoldOverTimePeriod> response = adminService.showSellerVehiclesSoldOverTimePeriod(testSeller.getId(),dateOne,dateTwo);

        assertEquals(response.getStatusCode(), HttpStatusCode.valueOf(200));
        assertEquals(Integer.valueOf(2),response.getBody().getVehicles());
        assertEquals("The seller identified by id " + testSeller.getId() + " has sold " + 2 + " vehicles between " + dateOne + " and " + dateTwo,response.getBody().getMessage());

    }

    @Test
    void showSellerVehiclesSoldOverTimePeriodMissingSeller() {
        SellerEntity testSeller = sellerRepository.saveAndFlush(createSeller());
        VehicleEntity testVehicleOne = vehicleRepository.save(createVehicleOrderable());
        VehicleEntity testVehicleTwo = vehicleRepository.save(createVehicleOrderable());
        ClientEntity testClient = clientRepository.save(createClient());
        LocalDateTime dateOne = LocalDateTime.now().minusMinutes(1);
        OrderEntity testOrderOne = orderRepository.save(createOrderWithArguments(testSeller,testClient,testVehicleOne));
        OrderEntity testOrderTwo = orderRepository.save(createOrderWithArguments(testSeller,testClient,testVehicleTwo));
        LocalDateTime dateTwo = LocalDateTime.now().plusMinutes(1);

        ResponseEntity<ShowSellerVehiclesSoldOverTimePeriod> response = adminService.showSellerVehiclesSoldOverTimePeriod(Long.valueOf(-1),dateOne,dateTwo);

        assertEquals(HttpStatusCode.valueOf(404),response.getStatusCode());
        assertEquals(Integer.valueOf(0),response.getBody().getVehicles());
        assertEquals("Seller with id " + Long.valueOf(-1) + " does not exist",response.getBody().getMessage());

    }

    @Test
    void showSellerVehiclesSoldOverTimePeriodInvalidDate() {
        SellerEntity testSeller = sellerRepository.saveAndFlush(createSeller());
        VehicleEntity testVehicleOne = vehicleRepository.save(createVehicleOrderable());
        VehicleEntity testVehicleTwo = vehicleRepository.save(createVehicleOrderable());
        ClientEntity testClient = clientRepository.save(createClient());
        LocalDateTime dateOne = null;
        OrderEntity testOrderOne = orderRepository.save(createOrderWithArguments(testSeller,testClient,testVehicleOne));
        OrderEntity testOrderTwo = orderRepository.save(createOrderWithArguments(testSeller,testClient,testVehicleTwo));
        LocalDateTime dateTwo = null;

        ResponseEntity<ShowSellerVehiclesSoldOverTimePeriod> response = adminService.showSellerVehiclesSoldOverTimePeriod(testSeller.getId(),dateOne,dateTwo);

        assertEquals(HttpStatusCode.valueOf(400),response.getStatusCode());
        assertEquals(Integer.valueOf(0),response.getBody().getVehicles());
        assertEquals("You did not provide two valid dates for the search.\n" +
                        "Please make sure that your input matches the following format: yyyy-MM-ddTHH:mm:ss\n" +
                        "Example: 2024-02-20T14:30:00"
                ,response.getBody().getMessage());

    }


}