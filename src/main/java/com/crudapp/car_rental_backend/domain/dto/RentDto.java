package com.crudapp.car_rental_backend.domain.dto;

import com.crudapp.car_rental_backend.domain.RentStatus;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class RentDto {

    private Long rentId;
    private RentStatus rentStatus;
    private LocalDate creationDate;
    private Long customerId;
    private List<Long> carsId;
}
