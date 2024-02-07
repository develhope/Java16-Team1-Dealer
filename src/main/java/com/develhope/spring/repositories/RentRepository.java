package com.develhope.spring.repositories;

import com.develhope.spring.entities.order.OrderEntity;
import com.develhope.spring.entities.rent.RentEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
public interface RentRepository extends JpaRepository<RentEntity, Long> {

    @Query(value = "SELECT * FROM rents AS r WHERE o.id_client = :idClient", nativeQuery = true)
    List<RentEntity> showRentList(@Param("idClient") Long idClient);

    @Transactional
    @Modifying
    @Query(value = "DELETE FROM rents AS r WHERE r.id_client = :idClient AND r.id = :id", nativeQuery = true)
    void customDeleteById(@Param("idClient") Long idClient, @Param("id") Long idRent);
}
