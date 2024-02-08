package com.develhope.spring.dto.order;

import com.develhope.spring.entities.order.OrderState;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class PurchaseClientDTO {

    private BigDecimal advPayment;

    private Boolean isPaid;

    private OrderState orderState;

    private Long idSeller;

    private Long idVehicle;


}
