package com.develhope.spring.response.clientControllerResponse;

import com.develhope.spring.entities.order.OrderEntity;
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
