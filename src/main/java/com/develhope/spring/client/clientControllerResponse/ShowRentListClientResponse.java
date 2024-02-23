package com.develhope.spring.client.clientControllerResponse;

import com.develhope.spring.rent.RentDtoOutput;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ShowRentListClientResponse {
    private String message;
    private List<RentDtoOutput> rentDtoOutputList;
}
