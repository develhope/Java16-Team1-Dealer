package com.develhope.spring.response.order;

import com.develhope.spring.entities.order.OrderEntity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ListOrderResponse {

    private String messageError;
    private List<OrderEntity> orderEntityList;
}
