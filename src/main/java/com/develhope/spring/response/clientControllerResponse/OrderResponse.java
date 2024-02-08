package com.develhope.spring.response.clientControllerResponse;

import com.develhope.spring.entities.order.OrderEntity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class OrderResponse {

    private String messageError;
    private OrderEntity orderEntity;
}
