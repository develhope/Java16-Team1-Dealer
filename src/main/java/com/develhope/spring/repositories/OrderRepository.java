package com.develhope.spring.repositories;

import com.develhope.spring.entities.order.OrderEntity;
import com.develhope.spring.entities.order.OrderState;
import com.develhope.spring.entities.user.SellerEntity;
import com.develhope.spring.entities.user.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.time.LocalDateTime;
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

    @Query(value = "SELECT * FROM orders AS o WHERE o.order_stat = :#{#status.toString()}",nativeQuery = true)
    List<OrderEntity> showListByStatus(@Param("status") OrderState status);

    @Modifying
    @Query(value = "UPDATE orders AS o SET o.order_stat = 'CANCELED' WHERE o.id = :idOrder AND o.order_type = 'PURCHASE'",nativeQuery = true)
    void updateStatusCancelledPurchaseWithId(@Param("idOrder") Long idOrder);

    @Query(value = "SELECT COUNT(*), u.name, u.surname FROM orders JOIN users AS u ON orders.id_seller = u.id WHERE u.id = :idSeller AND orders.date_purch BETWEEN :d1 AND :d2", nativeQuery = true)
    int checkNumberOfSalesSeller(@Param("idSeller") Long idSeller, @Param("d1") LocalDate firstDate, @Param("d2") LocalDate secondDate);

    @Query(value = "SELECT u.*, SUM(o.adv_payment) AS sales FROM users AS u JOIN orders AS o ON u.id = o.id_seller WHERE u.`type` = 'SELLER' GROUP BY u.id HAVING sales BETWEEN :firstRange AND :secondRange", nativeQuery = true)
    List<SellerEntity> salesOfSellerInRangePrice(@Param("firstRange") int firstRange, @Param("secondRange") int secondRange);

    @Query(value = "SELECT SUM(o.adv_payment) AS sales FROM users AS u JOIN orders AS o ON u.id = o.id_seller WHERE u.id = :idSeller AND o.date_purch BETWEEN :d1 AND :d2 GROUP BY u.id", nativeQuery = true)
    int showProfitInPerioRange(@Param("idSeller") Long idSeller, @Param("d1") LocalDate firstDate, @Param("d2") LocalDate secondDate);
}
