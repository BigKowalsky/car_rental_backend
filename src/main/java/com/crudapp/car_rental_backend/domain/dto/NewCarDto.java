package com.crudapp.car_rental_backend.domain.dto;

import com.crudapp.car_rental_backend.domain.CarStatus;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class NewCarDto {

    private String model;
    private int productionYear;
    private BigDecimal basicPrice;
    private CarStatus carStatus;
    private Long segmentId;
}
