package com.develhope.spring.repositories;

import com.develhope.spring.entities.order.OrderEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OrderRepository extends JpaRepository<OrderEntity, Long> {

    @Query(value = "SELECT * FROM orders AS o \n" +
            "WHERE o.id_client = :idC", nativeQuery = true)
    List<OrderEntity> orderListIdClient(@Param("idC") Long idClient);


}
