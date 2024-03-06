package com.develhope.spring.seller.sellerControllerResponse;


import com.develhope.spring.rent.RentDtoInput;
import com.develhope.spring.rent.RentEntity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RentCreationFromSellerResponse {

    private String message;
    private RentDtoInput rentEntity;
}
