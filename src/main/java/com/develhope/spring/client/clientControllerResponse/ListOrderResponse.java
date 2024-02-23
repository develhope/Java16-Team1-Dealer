package com.develhope.spring.client.clientControllerResponse;

import com.develhope.spring.order.OrderEntity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ListOrderResponse {

    private String message;
    private List<OrderEntity> orderEntityList;
}
