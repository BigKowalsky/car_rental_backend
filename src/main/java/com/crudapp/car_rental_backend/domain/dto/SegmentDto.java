package com.crudapp.car_rental_backend.domain.dto;

import com.crudapp.car_rental_backend.domain.Car;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class SegmentDto {

    private Long segmentId;
    private String segmentName;
    private String segmentDescription;
    private List<Long> carsId;
}
