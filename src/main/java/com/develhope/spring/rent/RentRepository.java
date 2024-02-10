package com.develhope.spring.rent;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import com.develhope.spring.rent.RentEntity;

import java.util.List;

@Repository
public interface RentRepository extends JpaRepository<RentEntity, Long> {

    @Query(value = "SELECT * FROM rents AS r WHERE r.id_client = :idClient AND r.rent_status = 'INPROGRESS'", nativeQuery = true)
    List<RentEntity> showRentList(@Param("idClient") Long idClient);

    @Transactional
    @Modifying
    @Query(value = "UPDATE rents AS r " +
            "SET r.rent_status = 'DELETED' " +
            "WHERE r.id_client = :idclient " +
            "AND r.id = :idrent", nativeQuery = true)
    void customDeleteById(@Param("idclient") Long idClient, @Param("idrent") Long idRent);
}
