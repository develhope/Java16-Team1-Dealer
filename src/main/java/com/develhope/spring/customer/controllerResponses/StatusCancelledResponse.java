package com.develhope.spring.customer.controllerResponses;

import com.develhope.spring.order.OrderEntity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class StatusCancelledResponse {

    private String message;
    private OrderEntity orderEntity;

}
