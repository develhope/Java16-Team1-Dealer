package com.develhope.spring;

import com.develhope.spring.admin.AdminEntity;
import com.develhope.spring.client.ClientEntity;
import com.develhope.spring.client.ClientRepository;
import com.develhope.spring.client.ClientService;
import com.develhope.spring.client.clientControllerResponse.*;
import com.develhope.spring.order.OrderEntity;
import com.develhope.spring.order.OrderRepository;
import com.develhope.spring.order.OrderState;
import com.develhope.spring.order.OrderType;
import com.develhope.spring.order.dto.OrderClientDTO;
import com.develhope.spring.order.dto.PurchaseClientDTO;
import com.develhope.spring.seller.SellerEntity;
import com.develhope.spring.seller.SellerRepository;
import com.develhope.spring.user.UserEntity;
import com.develhope.spring.user.UserType;
import com.develhope.spring.vehicle.GearType;
import com.develhope.spring.vehicle.SellType;
import com.develhope.spring.vehicle.VehicleEntity;
import com.develhope.spring.vehicle.VehicleRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.ResponseEntity;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.TestPropertySource;

import static org.assertj.core.api.Assertions.assertThat;

import java.math.BigDecimal;
import java.sql.*;
import java.time.LocalDate;
import java.time.LocalDateTime;

@SpringBootTest
@TestPropertySource(value = {"classpath:application-test.properties"})
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
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

	public PurchaseClientDTO createPurchaseDTO(SellerEntity seller, VehicleEntity vehicle) {
		return PurchaseClientDTO.builder()
				.advPayment(BigDecimal.valueOf(200))
				.isPaid(Boolean.TRUE)
				.orderState(OrderState.DELIVERED)
				.dateOrder(LocalDateTime.now())
				.idSeller(seller.getId())
				.idVehicle(vehicle.getId()).build();
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


	@Test
	void createOrderTest() throws SQLException {
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
	void createOrderTestIDVehicleNotOrderable() throws SQLException {
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


	@Test
	void updateStatusCancelTest() {
		ClientEntity client = createClient();
		VehicleEntity vehicle = createVehicleOrderable();
		SellerEntity seller = createSeller();
		clientRepository.save(client);
		sellerRepository.save(seller);
		vehicleRepository.save(vehicle);

		OrderEntity orderEntity = createOrder(seller,client,vehicle);
		orderRepository.save(orderEntity);

		UserEntity user = clientRepository.findById(client.getId()).get();

		ResponseEntity<StatusCancelledResponse> statusCancelledResponse = clientService.updateStatusCancelled(orderEntity.getId());

		assertThat(statusCancelledResponse.getStatusCode().value()).isEqualTo(200);
		assertThat(statusCancelledResponse.getBody().getOrderEntity().getId()).isEqualTo(orderEntity.getId());
		assertThat(statusCancelledResponse.getBody().getMessage()).isEqualTo("Order with id " + orderEntity.getId() + " cancelled");

	}

	@Test
	void updateStatusCancelTestNoFoundOrder() {
		ClientEntity client = createClient();
		VehicleEntity vehicle = createVehicleOrderable();
		SellerEntity seller = createSeller();
		clientRepository.save(client);
		sellerRepository.save(seller);
		vehicleRepository.save(vehicle);

		UserEntity user = clientRepository.findById(client.getId()).get();

		ResponseEntity<StatusCancelledResponse> statusCancelledResponse = clientService.updateStatusCancelled(1L);

		assertThat(statusCancelledResponse.getStatusCode().value()).isEqualTo(404);
		assertThat(statusCancelledResponse.getBody().getOrderEntity().getId()).isNull();
		assertThat(statusCancelledResponse.getBody().getMessage()).isEqualTo("Order with id " + 1 + " does not exist");

	}

	@Test
	void createPurcaseTest() {
		ClientEntity client = createClient();
		VehicleEntity vehicle = createVehicleOrderable();
		vehicle.setSellType(SellType.RFD);
		SellerEntity seller = createSeller();
		clientRepository.save(client);
		sellerRepository.save(seller);
		vehicleRepository.save(vehicle);
		UserEntity user = clientRepository.findById(client.getId()).get();

		PurchaseClientDTO purchaseClientDTO = createPurchaseDTO(seller, vehicle);

		ResponseEntity<PurchaseResponse> purchaseResponse = clientService.createPurchase(user, purchaseClientDTO);

		assertThat(purchaseResponse.getStatusCode().value()).isEqualTo(201);
		assertThat(purchaseResponse.getBody().getOrderEntity().getId()).isNotNull();
		assertThat(purchaseResponse.getBody().getMessageError()).isEqualTo("Purchase created successfully");
	}

	@Test
	void createPurchaseTestIDVehicleNotOrderable() {
		ClientEntity client = createClient();
		VehicleEntity vehicle = createVehicleRentable();
		vehicle.setSellType(SellType.USED);
		SellerEntity seller = createSeller();
		clientRepository.save(client);
		sellerRepository.save(seller);
		vehicleRepository.save(vehicle);
		UserEntity user = clientRepository.findById(client.getId()).get();

		PurchaseClientDTO purchaseClientDTO = createPurchaseDTO(seller, vehicle);

		ResponseEntity<PurchaseResponse> purchaseResponse = clientService.createPurchase(user, purchaseClientDTO);

		assertThat(purchaseResponse.getStatusCode().value()).isEqualTo(602);
		assertThat(purchaseResponse.getBody().getOrderEntity().getId()).isNull();
		assertThat(purchaseResponse.getBody().getMessageError()).isEqualTo("Vehicle with id " + purchaseClientDTO.getIdVehicle() + " is not purchasable");
	}
	@Test
	void createPPurchaseTestIDVehicleNotExist() {
		ClientEntity client = createClient();
		VehicleEntity vehicle = createVehicleRentable();
		SellerEntity seller = createSeller();
		clientRepository.save(client);
		sellerRepository.save(seller);
		vehicleRepository.save(vehicle);
		UserEntity user = clientRepository.findById(client.getId()).get();

		PurchaseClientDTO purchaseClientDTO = createPurchaseDTO(seller, vehicle);
		purchaseClientDTO.setIdVehicle(3L);

		ResponseEntity<PurchaseResponse> purchaseResponse = clientService.createPurchase(user, purchaseClientDTO);

		assertThat(purchaseResponse.getStatusCode().value()).isEqualTo(600);
		assertThat(purchaseResponse.getBody().getOrderEntity().getId()).isNull();
		assertThat(purchaseResponse.getBody().getMessageError()).isEqualTo("Vehicle with id " + purchaseClientDTO.getIdVehicle() + " does not exist");
	}


	@Test
	void purchaseListTestNoOrder() {
		ClientEntity client = createClient();
		clientRepository.save(client);
		UserEntity user = clientRepository.findById(client.getId()).get();

		ResponseEntity<ListPurchaseResponse> orderEntityListResponse = clientService.purchaseList(user);

		assertThat(orderEntityListResponse.getStatusCode().value()).isEqualTo(404);
		assertThat(orderEntityListResponse.getBody().getOrderEntityList().size()).isEqualTo(0);
		assertThat(orderEntityListResponse.getBody().getMessage()).isEqualTo("Purchases not found");
	}

	@Test
	void purchaseListTest() {
		ClientEntity client = createClient();
		VehicleEntity vehicle = createVehicleOrderable();
		SellerEntity seller = createSeller();
		clientRepository.save(client);
		sellerRepository.save(seller);
		vehicleRepository.save(vehicle);

		OrderEntity orderEntity = createOrder(seller,client,vehicle);
		orderEntity.setOrderType(OrderType.PURCHASE);
		orderRepository.save(orderEntity);

		UserEntity user = clientRepository.findById(client.getId()).get();

		ResponseEntity<ListPurchaseResponse> orderEntityListResponse = clientService.purchaseList(user);

		assertThat(orderEntityListResponse.getStatusCode().value()).isEqualTo(200);
		assertThat(orderEntityListResponse.getBody().getOrderEntityList().size()).isEqualTo(1);
		assertThat(orderEntityListResponse.getBody().getMessage()).isEqualTo("Purchases found");
	}

	@Test
	void filterByRangePriceTest() {
		VehicleEntity vehicle = createVehicleOrderable();
		vehicleRepository.save(vehicle);

		ResponseEntity<ListVehicleFilterResponse> listVehicleFilterResponse = clientService.filterFindVehicleByRangePrice(BigDecimal.valueOf(0), BigDecimal.valueOf(100000));

		assertThat(listVehicleFilterResponse.getStatusCode().value()).isEqualTo(302);
		assertThat(listVehicleFilterResponse.getBody().getVehicleEntityList().size()).isEqualTo(1);
		assertThat(listVehicleFilterResponse.getBody().getMessage()).isEqualTo("Vehicles found");
	}

	@Test
	void filterByRangePriceTestInvert() {
		VehicleEntity vehicle = createVehicleOrderable();
		vehicleRepository.save(vehicle);

		ResponseEntity<ListVehicleFilterResponse> listVehicleFilterResponse = clientService.filterFindVehicleByRangePrice(BigDecimal.valueOf(100000), BigDecimal.valueOf(0));

		assertThat(listVehicleFilterResponse.getStatusCode().value()).isEqualTo(302);
		assertThat(listVehicleFilterResponse.getBody().getVehicleEntityList().size()).isEqualTo(1);
		assertThat(listVehicleFilterResponse.getBody().getMessage()).isEqualTo("Vehicles found");
	}


	@Test
	void filterByRangePriceTestNotFound() {
		VehicleEntity vehicle = createVehicleOrderable();
		vehicleRepository.save(vehicle);

		ResponseEntity<ListVehicleFilterResponse> listVehicleFilterResponse = clientService.filterFindVehicleByRangePrice(BigDecimal.valueOf(0), BigDecimal.valueOf(1000));

		assertThat(listVehicleFilterResponse.getStatusCode().value()).isEqualTo(611);
		assertThat(listVehicleFilterResponse.getBody().getVehicleEntityList().size()).isEqualTo(0);
		assertThat(listVehicleFilterResponse.getBody().getMessage()).isEqualTo("Vehicles not found by range price");
	}

	@Test
	void showAllVehiclesFilterTest() {

	}




}
