package com.develhope.spring.seller.sellerControllerResponse;

import com.develhope.spring.rent.RentEntity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RentDeletionByIdFromSellerResponse {

    private String message;
    private RentEntity rentEntity;
}
