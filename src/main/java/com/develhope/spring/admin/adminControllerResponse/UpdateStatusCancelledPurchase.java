package com.develhope.spring.admin.adminControllerResponse;

import com.develhope.spring.order.OrderEntity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UpdateStatusCancelledPurchase {

    private String message;
    private OrderEntity orderEntity;
}
