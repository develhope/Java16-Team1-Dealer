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
    private long id;
    @OneToOne
    @JoinColumn(name = "id_seller", columnDefinition = "BIGINT(19) DEFAULT '0'", nullable = false)
    private UserEntity sellerId;
    @OneToOne
    @JoinColumn(name = "id_client", columnDefinition = "BIGINT(19) DEFAULT '0'", nullable = false)
    private UserEntity clientId;
    @OneToOne
    @JoinColumn(name = "id_vehicle", columnDefinition = "BIGINT(19) DEFAULT '0'", nullable = false)
    private VehicleEntity vehicleId;
    @Column(name = "adv_payment", columnDefinition = "DECIMAL(20,6) DEFAULT '0.000000'", nullable = false)
    private BigDecimal advPayment;
    @Column(name = "is_paid", columnDefinition = "TINYINT(3)", nullable = false)
    private boolean isPaid;
    @Column(name = "order_stat", columnDefinition = "ENUM('PENDING','SHIPPED','DELIVERED','CANCELLED'", nullable = false)
    private OrderState orderState;

}
