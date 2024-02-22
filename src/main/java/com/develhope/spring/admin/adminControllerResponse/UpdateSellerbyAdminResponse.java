package com.develhope.spring.admin.adminControllerResponse;

import com.develhope.spring.seller.SellerEntity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UpdateSellerbyAdminResponse {

    private String message;

    private SellerEntity sellerEntity;

}
