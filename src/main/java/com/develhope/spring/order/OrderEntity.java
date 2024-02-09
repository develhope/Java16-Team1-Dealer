package com.develhope.spring.order;

import com.develhope.spring.user.UserEntity;
import com.develhope.spring.shared.vehicle.VehicleEntity;
import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;


@Data
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "orders")
public class OrderEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "id_seller", nullable = false)
    private UserEntity sellerId;

    @ManyToOne
    @JoinColumn(name = "id_client", nullable = false)
    private UserEntity clientId;

    @ManyToOne
    @JoinColumn(name = "id_vehicle", nullable = false)
    private VehicleEntity vehicleId;

    @Column(name = "adv_payment", nullable = false)
    private BigDecimal advPayment;

    @Column(name = "is_paid", nullable = false)
    private Boolean isPaid;

    @Column(name = "date_purch")
    private LocalDateTime datePurchase;

    @Column(name = "order_stat", nullable = false)
    @Enumerated(EnumType.STRING)
    private OrderState orderState;

    @Column(name = "order_type", nullable = false)
    @Enumerated(EnumType.STRING)
    private OrderType orderType;
}
