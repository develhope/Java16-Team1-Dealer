package com.develhope.spring.entities.order;

import com.develhope.spring.entities.user.ClientEntity;
import com.develhope.spring.entities.user.SellerEntity;
import com.develhope.spring.entities.user.UserEntity;
import com.develhope.spring.entities.vehicle.VehicleEntity;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.apache.catalina.User;

import java.math.BigDecimal;


@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "orders")
public class OrderEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @OneToOne
    @JoinColumn(name = "id_seller", nullable = false)
    private UserEntity sellerId;
    @OneToOne
    @JoinColumn(name = "id_client", nullable = false)
    private UserEntity clientId;
    @OneToOne
    @JoinColumn(name = "id_vehicle", nullable = false)
    private VehicleEntity vehicleId;
    @Column(name = "adv_payment", nullable = false)
    private BigDecimal advPayment;
    @Column(name = "is_paid", nullable = false)
    private boolean isPaid;
    @Column(name = "order_stat", nullable = false)
    private OrderState orderState;

}
