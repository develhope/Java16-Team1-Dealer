package com.develhope.spring.client.clientControllerResponse;

import com.develhope.spring.rent.RentDtoInput;
import com.develhope.spring.rent.RentDtoOutput;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class NewRentResponse {
    private String message;
    private RentDtoOutput rentDtoInput;
}
