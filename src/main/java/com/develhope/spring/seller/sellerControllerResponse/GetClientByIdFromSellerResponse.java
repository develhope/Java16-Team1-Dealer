package com.develhope.spring.seller.sellerControllerResponse;


import com.develhope.spring.client.ClientEntity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class GetClientByIdFromSellerResponse {

    private String message;
    private ClientEntity clientEntity;
}
