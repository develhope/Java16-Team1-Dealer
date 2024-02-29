package com.develhope.spring;

import com.develhope.spring.admin.AdminEntity;
import com.develhope.spring.client.ClientEntity;
import com.develhope.spring.client.ClientRepository;
import com.develhope.spring.client.ClientService;
import com.develhope.spring.client.clientControllerResponse.ListOrderResponse;
import com.develhope.spring.client.clientControllerResponse.OrderResponse;
import com.develhope.spring.client.clientControllerResponse.ShowVehicleIDResponse;
import com.develhope.spring.client.clientControllerResponse.UpdateAccountResponse;
import com.develhope.spring.order.OrderEntity;
import com.develhope.spring.order.OrderRepository;
import com.develhope.spring.order.OrderState;
import com.develhope.spring.order.OrderType;
import com.develhope.spring.order.dto.OrderClientDTO;
import com.develhope.spring.seller.SellerEntity;
import com.develhope.spring.seller.SellerRepository;
import com.develhope.spring.user.UserEntity;
import com.develhope.spring.user.UserRepository;
import com.develhope.spring.user.UserType;
import com.develhope.spring.vehicle.GearType;
import com.develhope.spring.vehicle.SellType;
import com.develhope.spring.vehicle.VehicleEntity;
import com.develhope.spring.vehicle.VehicleRepository;
import org.junit.After;
import org.junit.Before;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.User;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.event.annotation.BeforeTestClass;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.http.HttpStatus.CREATED;


import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

@SpringBootTest
@TestPropertySource(value = {"classpath:application-test.properties"})
class ClientServiceTest {
	@Autowired
	private OrderRepository orderRepository;
	@Autowired
	private ClientService clientService;
	@Autowired
	private ClientRepository clientRepository;
	@Autowired
	private SellerRepository sellerRepository;
	@Autowired
	private VehicleRepository vehicleRepository;

//	@Before
//	public void setUp() {
//		ClientEntity client = createClient();
//		VehicleEntity vehicleOrderable = createVehicleOrderable();
//		VehicleEntity vehicleNotOrderable = createVehicleRentable();
//		SellerEntity seller = createSeller();
//		clientRepository.save(client); //ID ACCOUNT 1
//		sellerRepository.save(seller); //ID ACCOUNT 2
//		vehicleRepository.save(vehicleOrderable); //ID VEHICLE 1
//		vehicleRepository.save(vehicleNotOrderable); // ID VEHICLE 2
//
//		System.out.println(client);
//		System.out.println(seller);
//		System.out.println(vehicleOrderable);
//	}



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

	public UserEntity createUser() {
		return UserEntity.builder()
				.name("Bruno")
				.surname("Orlandi")
				.email("bruno@gmail.com")
				.psw("ciaociao")
				.type(UserType.CLIENT)
				.build();
	}


	public OrderClientDTO createOrderDTO() {
		return OrderClientDTO.builder()
				.advPayment(BigDecimal.valueOf(200))
				.isPaid(Boolean.TRUE)
				.orderState(OrderState.DELIVERED)
				.dateOrder(LocalDateTime.now())
				.idSeller(Long.valueOf(2))
				.idVehicle(Long.valueOf(1)).build();
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

	public VehicleEntity createVehicleRentable() {
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
				.sellType(SellType.USED).build();
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

//	@Test
//	@Before
//    public void setUPTest(){
//		setUp();
//	}

	@Test
	void createOrderTest() {
		ClientEntity client = createClient();
		OrderClientDTO orderClientDTO = createOrderDTO();
//		orderClientDTO.setIdVehicle(1L);
		VehicleEntity vehicle = createVehicleOrderable();
		SellerEntity seller = createSeller();
		clientRepository.save(client);
		sellerRepository.save(seller);
		vehicleRepository.save(vehicle);
		UserEntity user = clientRepository.findById(client.getId()).get();


		ResponseEntity<OrderResponse> orderResponse = clientService.createOrder(user, orderClientDTO);

		assertThat(orderResponse.getStatusCode().value()).isEqualTo(201);
		assertThat(orderResponse.getBody().getOrderEntity().getId()).isNotNull();
		assertThat(orderResponse.getBody().getMessage()).isEqualTo("Order created successfully");
	}

	@Test
	void createOrderTestIDVehicleNotOrderable() {
		ClientEntity client = createClient();
		OrderClientDTO orderClientDTO = createOrderDTO();
//		orderClientDTO.setIdVehicle(2L);
		VehicleEntity vehicle = createVehicleRentable();
		SellerEntity seller = createSeller();
		clientRepository.save(client);
		sellerRepository.save(seller);
		vehicleRepository.save(vehicle);
		UserEntity user = clientRepository.findById(client.getId()).get();


		ResponseEntity<OrderResponse> orderResponse = clientService.createOrder(user, orderClientDTO);

		assertThat(orderResponse.getStatusCode().value()).isEqualTo(602);
		assertThat(orderResponse.getBody().getOrderEntity().getId()).isNull();
		assertThat(orderResponse.getBody().getMessage()).isEqualTo("Vehicle with id " + orderClientDTO.getIdVehicle() + " is not orderable");
	}
	@Test
	void createOrderTestIDVehicleNotExist() {
		ClientEntity client = createClient();
		OrderClientDTO orderClientDTO = createOrderDTO();
		orderClientDTO.setIdVehicle(2L);
		VehicleEntity vehicle = createVehicleRentable();
		SellerEntity seller = createSeller();
		clientRepository.save(client);
		sellerRepository.save(seller);
		vehicleRepository.save(vehicle);
		UserEntity user = clientRepository.findById(client.getId()).get();


		ResponseEntity<OrderResponse> orderResponse = clientService.createOrder(user, orderClientDTO);

		assertThat(orderResponse.getStatusCode().value()).isEqualTo(600);
		assertThat(orderResponse.getBody().getOrderEntity().getId()).isNull();
		assertThat(orderResponse.getBody().getMessage()).isEqualTo("Vehicle with id " + orderClientDTO.getIdVehicle() + " does not exist");
	}

	@Test
	void showVehicleIDTest() {
		VehicleEntity vehicle = createVehicleOrderable();
		vehicleRepository.save(vehicle);
		ResponseEntity<ShowVehicleIDResponse> showVehicleIDResponse = clientService.showVehicleID(vehicle.getId());
		assertThat(showVehicleIDResponse.getStatusCode().value()).isEqualTo(302);
		assertThat(showVehicleIDResponse.getBody().getVehicleEntity().getId()).isEqualTo(vehicle.getId());
		assertThat(showVehicleIDResponse.getBody().getMessage()).isEqualTo("Vehicle found");
	}

	@Test
	void showVehicleIDTestNotFound() {
		ResponseEntity<ShowVehicleIDResponse> showVehicleIDResponse = clientService.showVehicleID(1L);
		assertThat(showVehicleIDResponse.getStatusCode().value()).isEqualTo(404);
		assertThat(showVehicleIDResponse.getBody().getVehicleEntity().getId()).isNull();
		assertThat(showVehicleIDResponse.getBody().getMessage()).isEqualTo("Vehicle not found");
	}

	@Test
	void updateAccountTest() {
		ClientEntity client = createClient();
		clientRepository.save(client);
		UserEntity user = clientRepository.findById(client.getId()).get();
		ClientEntity clientEntity = createClient();
		clientEntity.setName("Francesco");
		clientEntity.setSurname(null);
		clientEntity.setPhone(null);
		clientEntity.setPsw(null);
		ResponseEntity<UpdateAccountResponse> updateAccountResponse = clientService.updateAccount(user, clientEntity);
		assertThat(updateAccountResponse.getStatusCode().value()).isEqualTo(607);
		assertThat(updateAccountResponse.getBody().getClientEntity().getId()).isEqualTo(user.getId());
		assertThat(updateAccountResponse.getBody().getMessage()).isEqualTo("Account updated successfully");
	}

	@Test
	void updateAccountTestClientEntityNull() {
		ClientEntity client = createClient();
		clientRepository.save(client);
		UserEntity user = clientRepository.findById(client.getId()).get();
		ClientEntity clientEntity = null;
		ResponseEntity<UpdateAccountResponse> updateAccountResponse = clientService.updateAccount(user, clientEntity);
		assertThat(updateAccountResponse.getStatusCode().value()).isEqualTo(406);
		assertThat(updateAccountResponse.getBody().getClientEntity().getId()).isEqualTo(user.getId());
		assertThat(updateAccountResponse.getBody().getMessage()).isEqualTo("Please enter details to update account");
	}

	@Test
	void deleteAccountTest() {
		ClientEntity client = createClient();
		clientRepository.save(client);
		UserEntity user = clientRepository.findById(client.getId()).get();
		ResponseEntity<String> deleteAccountResponse = clientService.deleteAccount(user);

		assertThat(deleteAccountResponse.getStatusCode().value()).isEqualTo(200);
		assertThat(deleteAccountResponse.getBody()).isEqualTo("Account deleted successfully");
	}

	@Test
	void orderEntityListTestNoOrder() {
		ClientEntity client = createClient();
		clientRepository.save(client);
		UserEntity user = clientRepository.findById(client.getId()).get();

		ResponseEntity<ListOrderResponse> orderEntityListResponse = clientService.orderEntityList(user);

		assertThat(orderEntityListResponse.getStatusCode().value()).isEqualTo(404);
		assertThat(orderEntityListResponse.getBody().getOrderEntityList().size()).isEqualTo(0);
		assertThat(orderEntityListResponse.getBody().getMessage()).isEqualTo("Orders not found");
	}

	@Test
	void orderEntityListTest() {
		ClientEntity client = createClient();
		VehicleEntity vehicle = createVehicleOrderable();
		SellerEntity seller = createSeller();
		clientRepository.save(client);
		sellerRepository.save(seller);
		vehicleRepository.save(vehicle);

		OrderEntity orderEntity = createOrder(seller,client,vehicle);
		orderRepository.save(orderEntity);

		UserEntity user = clientRepository.findById(client.getId()).get();

		ResponseEntity<ListOrderResponse> orderEntityListResponse = clientService.orderEntityList(user);

		assertThat(orderEntityListResponse.getStatusCode().value()).isEqualTo(200);
		assertThat(orderEntityListResponse.getBody().getOrderEntityList().size()).isEqualTo(1);
		assertThat(orderEntityListResponse.getBody().getMessage()).isEqualTo("Orders found");
	}






}
