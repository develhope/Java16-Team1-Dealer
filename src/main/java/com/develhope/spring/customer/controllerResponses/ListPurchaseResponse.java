package com.develhope.spring.customer.controllerResponses;

import com.develhope.spring.order.OrderEntity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ListPurchaseResponse {

    private String message;
    private List<OrderEntity> orderEntityList;
}
