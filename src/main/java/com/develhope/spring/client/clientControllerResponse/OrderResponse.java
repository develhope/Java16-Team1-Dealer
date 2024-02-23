package com.develhope.spring.client.clientControllerResponse;

import com.develhope.spring.order.OrderEntity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class OrderResponse {

    private String message;
    private OrderEntity orderEntity;
}
