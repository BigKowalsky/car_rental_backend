package com.crudapp.car_rental_backend.controller;

import com.crudapp.car_rental_backend.controller.exceptions.CarNotFoundException;
import com.crudapp.car_rental_backend.controller.exceptions.SegmentNotFoundException;
import com.crudapp.car_rental_backend.domain.Car;
import com.crudapp.car_rental_backend.domain.dto.CarDto;
import com.crudapp.car_rental_backend.domain.dto.NewCarDto;
import com.crudapp.car_rental_backend.mapper.CarMapper;
import com.crudapp.car_rental_backend.service.CarService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/v1/car")
public class CarController {

    private final CarMapper carMapper;
    private final CarService carService;

    @GetMapping
    public ResponseEntity<List<CarDto>>getAllCars() {
        return ResponseEntity.ok(carMapper.mapToCarDtoList(carService.getAllCars()));
    }

    @GetMapping(value = "{carId}")
    public ResponseEntity<CarDto> getCar(@PathVariable Long carId) throws CarNotFoundException {
        return ResponseEntity.ok(carMapper.mapToCarDto(carService.getCarById(carId)));
    }

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Void> createCar(@RequestBody NewCarDto newCarDto) throws SegmentNotFoundException {
        Car car = carMapper.mapToNewCar(newCarDto);
        carService.createCar(car);
        return ResponseEntity.ok().build();
    }

    @PutMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<CarDto> updateCar(@RequestBody CarDto carDto) throws CarNotFoundException, SegmentNotFoundException {
        return ResponseEntity.ok(carMapper.mapToCarDto(carService.updateCar(carMapper.mapToCar(carDto))));
    }

    @DeleteMapping(value = "{carId}")
    public ResponseEntity<Void> deleteCar(@PathVariable Long carId) {
        carService.deleteCar(carId);
        return ResponseEntity.ok().build();
    }
}
