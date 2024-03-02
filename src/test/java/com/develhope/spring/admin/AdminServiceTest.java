package com.develhope.spring.admin;

import com.develhope.spring.admin.adminControllerResponse.ShowSellerVehiclesSoldOverTimePeriod;
import com.develhope.spring.client.ClientEntity;
import com.develhope.spring.client.ClientRepository;
import com.develhope.spring.client.ClientService;
import com.develhope.spring.order.OrderEntity;
import com.develhope.spring.order.OrderRepository;
import com.develhope.spring.order.OrderState;
import com.develhope.spring.order.OrderType;
import com.develhope.spring.seller.SellerEntity;
import com.develhope.spring.seller.SellerRepository;
import com.develhope.spring.user.UserType;
import com.develhope.spring.vehicle.GearType;
import com.develhope.spring.vehicle.SellType;
import com.develhope.spring.vehicle.VehicleEntity;
import com.develhope.spring.vehicle.VehicleRepository;
import org.junit.jupiter.api.BeforeEach;
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

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@TestPropertySource(value = {"classpath:application-test.properties"})
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
class AdminServiceTestIntegration {


    @Autowired
    private OrderRepository orderRepository;
    @Autowired
    private AdminService adminService;
    @Autowired
    private ClientRepository clientRepository;
    @Autowired
    private VehicleRepository vehicleRepository;
    @Autowired
    private SellerRepository sellerRepository;

    public SellerEntity createSeller() {
        SellerEntity seller = new SellerEntity();
        seller.setName("Pippo");
        seller.setSurname("Topolino");
        seller.setEmail("pippo@gmail.com");
        seller.setPsw("ciaociao");
        seller.setType(UserType.SELLER);

        return seller;
    }

    public AdminEntity createAdmin() {
        AdminEntity admin = new AdminEntity();
        admin.setName("Antonio");
        admin.setSurname("Orlandi");
        admin.setEmail("boh@gmail.com");
        admin.setPsw("ciaociao");
        admin.setType(UserType.ADMIN);

        return admin;
    }


    public ClientEntity createClient() {
        ClientEntity client = new ClientEntity();
        client.setName("Bruno");
        client.setSurname("Orlandi");
        client.setEmail("bruno@gmail.com");
        client.setPsw("ciaociao");
        client.setType(UserType.CLIENT);
        return client;
    }

    public OrderEntity createOrder(SellerEntity seller, ClientEntity client, VehicleEntity vehicle) {
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

    public VehicleEntity createVehicleOrderable() {
        return VehicleEntity.builder()
                .brand("Volkswagen")
                .model("Passat")
                .engineCapacity(2)
                .colour("blue")
                .hp(200)
                .gearType(GearType.MANUAL)
                .registerYear(LocalDate.now())
                .fuelType("benzina")
                .price(BigDecimal.valueOf(20000))
                .priceDscnt(0)
                .accessories("GPS")
                .rentable(false)
                .sellType(SellType.ORDERABLE).build();
    }



    @Test
    void showSellerRevenueOverTimePeriodOkResponse() {
        SellerEntity testSeller = sellerRepository.saveAndFlush(createSeller());
        VehicleEntity testVehicleOne = vehicleRepository.save(createVehicleOrderable());
        VehicleEntity testVehicleTwo = vehicleRepository.save(createVehicleOrderable());
        ClientEntity testClient = clientRepository.save(createClient());
        LocalDateTime dateOne = LocalDateTime.now().minusMonths(1);
        OrderEntity testOrderOne = orderRepository.save(createOrder(testSeller,testClient,testVehicleOne));
        OrderEntity testOrderTwo = orderRepository.save(createOrder(testSeller,testClient,testVehicleTwo));
        LocalDateTime dateTwo = LocalDateTime.now().plusMonths(1);

        ResponseEntity<ShowSellerRevenueOverTimePeriod> response = adminService.showSellerRevenueOverTimePeriod(testSeller.getId(),dateOne,dateTwo);
        response.getBody().setRevenue(response.getBody().getRevenue().setScale(2));

        assertEquals(response.getStatusCode(), HttpStatusCode.valueOf(200));
        assertEquals(BigDecimal.valueOf(40000).setScale(2),response.getBody().getRevenue());
        assertEquals("The seller identified by id " + testSeller.getId() + " has earned a revenue of " + BigDecimal.valueOf(40000).setScale(2) + " between " + dateOne + " and " + dateTwo,response.getBody().getMessage());

    }

    @Test
    void showSellerRevenueOverTimePeriodMissingSeller() {
        SellerEntity testSeller = sellerRepository.saveAndFlush(createSeller());
        VehicleEntity testVehicleOne = vehicleRepository.save(createVehicleOrderable());
        VehicleEntity testVehicleTwo = vehicleRepository.save(createVehicleOrderable());
        ClientEntity testClient = clientRepository.save(createClient());
        LocalDateTime dateOne = LocalDateTime.now().minusMonths(1);
        OrderEntity testOrderOne = orderRepository.save(createOrder(testSeller,testClient,testVehicleOne));
        OrderEntity testOrderTwo = orderRepository.save(createOrder(testSeller,testClient,testVehicleTwo));
        LocalDateTime dateTwo = LocalDateTime.now().plusMonths(1);

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
        OrderEntity testOrderOne = orderRepository.save(createOrder(testSeller,testClient,testVehicleOne));
        OrderEntity testOrderTwo = orderRepository.save(createOrder(testSeller,testClient,testVehicleTwo));
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
        LocalDateTime dateOne = LocalDateTime.now().minusMonths(1);
        OrderEntity testOrderOne = orderRepository.save(createOrder(testSeller,testClient,testVehicleOne));
        OrderEntity testOrderTwo = orderRepository.save(createOrder(testSeller,testClient,testVehicleTwo));
        LocalDateTime dateTwo = LocalDateTime.now().plusMonths(1);

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
        LocalDateTime dateOne = LocalDateTime.now().minusMonths(1);
        OrderEntity testOrderOne = orderRepository.save(createOrder(testSeller,testClient,testVehicleOne));
        OrderEntity testOrderTwo = orderRepository.save(createOrder(testSeller,testClient,testVehicleTwo));
        LocalDateTime dateTwo = LocalDateTime.now().plusMonths(1);

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
        OrderEntity testOrderOne = orderRepository.save(createOrder(testSeller,testClient,testVehicleOne));
        OrderEntity testOrderTwo = orderRepository.save(createOrder(testSeller,testClient,testVehicleTwo));
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