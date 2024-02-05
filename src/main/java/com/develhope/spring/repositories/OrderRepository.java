package com.develhope.spring.repositories;

import com.develhope.spring.entities.order.OrderEntity;
import com.develhope.spring.entities.order.OrderState;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OrderRepository extends JpaRepository<OrderEntity, Long> {

    @Query(value = "SELECT * FROM orders AS o WHERE o.id_client = :idClient AND o.order_type = 'ORDER'",nativeQuery = true)
    List<OrderEntity> showListOrder(@Param("idClient")Long idClient);
    @Modifying
    @Query(value = "UPDATE orders AS o SET o.order_stat = 'CANCELED' WHERE o.id = :idOrder AND o.order_type = 'ORDER'",nativeQuery = true)
    void updateStatusCancelledOrderWithId(@Param("idOrder") Long idOrder);

    @Query(value = "SELECT * FROM orders AS o WHERE o.id_client = :idClient AND o.order_type = 'PURCHASE'",nativeQuery = true)
    List<OrderEntity> showListPurchase(@Param("idClient")Long idClient);

    @Query(value = "SELECT * FROM orders AS o WHERE o.order_stat = :#{#status.toString().toUpperCase()}",nativeQuery = true)
    List<OrderEntity> showListByStatus(@Param("status") OrderState status);



}
