package com.develhope.spring.admin.adminControllerResponse;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ShowSellerVehiclesSoldOverTimePeriod {
    private String message;
    private Integer vehicles;
}
