package com.develhope.spring.customer;

import com.develhope.spring.order.OrderEntity;
import com.develhope.spring.rent.RentEntity;
import com.develhope.spring.user.UserEntity;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.OneToMany;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Entity(name = "client")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class CustomerEntity extends UserEntity {

    private String phone;

    @OneToMany(mappedBy = "clientId", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonIgnore
    private List<OrderEntity> orderList;

    @OneToMany(mappedBy = "clientId", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonIgnore
    private List<RentEntity> rentList;


}