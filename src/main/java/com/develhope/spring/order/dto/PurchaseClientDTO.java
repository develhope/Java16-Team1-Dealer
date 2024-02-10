package com.develhope.spring.order.dto;

import com.develhope.spring.order.OrderState;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class PurchaseClientDTO {

    private BigDecimal advPayment;

    private Boolean isPaid;

    private OrderState orderState;

    private LocalDateTime dateOrder;

    private Long idSeller;

    private Long idVehicle;


}
