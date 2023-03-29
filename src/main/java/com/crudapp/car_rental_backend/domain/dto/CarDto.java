package com.crudapp.car_rental_backend.domain.dto;

import com.crudapp.car_rental_backend.domain.CarStatus;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

import java.math.BigDecimal;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CarDto {

    private Long carId;
    private String model;
    private int productionYear;
    private BigDecimal basicPrice;
    private CarStatus carStatus;
    private Long segmentId;
    private List<Long> rentsId;
}
