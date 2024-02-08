package com.develhope.spring.dto;

import com.develhope.spring.entities.order.OrderState;
import jakarta.persistence.Column;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class OrderClientDTO {

    private BigDecimal advPayment;

    private Boolean isPaid;

    private OrderState orderState;

    private Long idSeller;

    private Long idVehicle;


}
