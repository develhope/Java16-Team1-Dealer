package com.develhope.spring.seller;

import com.develhope.spring.user.UserEntity;
import com.develhope.spring.order.OrderEntity;
import com.develhope.spring.rent.*;
import com.fasterxml.jackson.annotation.JsonIgnore;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.OneToMany;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Entity(name = "seller")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Schema(name = "Seller Accout", description = "Seller Account")
public class SellerEntity extends UserEntity {
    @Schema(description = "Phone number", example = "1234567890", required = true)
    private String phone;

    @OneToMany(mappedBy = "sellerId", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonIgnore
    private List<OrderEntity> orderListSeller;

    @OneToMany(mappedBy = "sellerId", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonIgnore
    private List<RentEntity> rentListSell;

}