package com.develhope.spring.seller;

import com.develhope.spring.user.UserEntity;
import com.develhope.spring.order.OrderEntity;
import com.develhope.spring.rent.*;
import com.develhope.spring.vehicle.GearType;
import com.develhope.spring.vehicle.VehicleSalesInfoDto;
import com.fasterxml.jackson.annotation.JsonIgnore;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.List;


@NamedNativeQueries({
        @NamedNativeQuery(
                name = "showRevenueOverTimePeriod",
                query = "SELECT SUM(v.price - v.price_dscnt) AS earnings" +
                        "FROM orders AS o " +
                        "JOIN vehicle AS v ON o.id_vehicle = v.id " +
                        "WHERE o.id_seller = :id " +
                        "AND o.order_stat != 'CANCELED' " +
                        "AND o.date_purch >= :firstDate " +
                        "AND o.date_purch <= :secondDate ;",
                resultSetMapping = "seller_earnings_dto"
        ),
        @NamedNativeQuery(
                name = "showVehiclesSoldOverTimePeriod",
                query = "SELECT COUNT(*) AS sales" +
                        "FROM orders AS o " +
                        "WHERE o.id_seller = :id " +
                        "AND o.date_purch <= :firstDate " +
                        "AND o.date_purch >= :secondDate " +
                        "AND o.order_stat != 'CANCELED'",
                resultSetMapping = "seller_sales_dto"
        )
})
@SqlResultSetMappings({
        @SqlResultSetMapping(
                name = "seller_earnings_dto",
                classes = @ConstructorResult(
                        targetClass = SellerEarningsDto.class,
                        columns = {
                                @ColumnResult(name = "earnings", type = BigDecimal.class)
                        }
                )
        ),
        @SqlResultSetMapping(
                name = "seller_sales_dto",
                classes = @ConstructorResult(
                        targetClass = SellerSalesDto.class,
                        columns = {
                                @ColumnResult(name = "sales", type = BigDecimal.class)
                        }
                )
        ),

})
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