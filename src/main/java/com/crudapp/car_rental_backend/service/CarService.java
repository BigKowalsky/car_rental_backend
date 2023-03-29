package com.crudapp.car_rental_backend.service;

import com.crudapp.car_rental_backend.controller.exceptions.CarNotFoundException;
import com.crudapp.car_rental_backend.domain.Car;
import com.crudapp.car_rental_backend.repository.CarRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
public class CarService {

    private final CarRepository carRepository;

    public List<Car> getAllCars() {
        return carRepository.findAll();
    }

    public Car getCarById(Long carId) throws CarNotFoundException {
        return carRepository.findById(carId).orElseThrow(CarNotFoundException::new);
    }

    public void createCar(Car car) {
        carRepository.save(car);
    }

    public Car updateCar(Car car) throws CarNotFoundException {
        Car updatedCar = getCarById(car.getCarId());
        updatedCar.setModel(car.getModel());
        updatedCar.setProductionYear(car.getProductionYear());
        updatedCar.setBasicPrice(car.getBasicPrice());
        updatedCar.setCarStatus(car.getCarStatus());
        updatedCar.setSegment(car.getSegment());
        updatedCar.setRents(car.getRents());
        carRepository.save(updatedCar);
        return updatedCar;
    }

    public void deleteCar(Long carId) {
        carRepository.deleteById(carId);
    }
}
