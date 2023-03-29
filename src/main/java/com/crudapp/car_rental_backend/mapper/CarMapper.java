package com.crudapp.car_rental_backend.mapper;

import com.crudapp.car_rental_backend.controller.exceptions.SegmentNotFoundException;
import com.crudapp.car_rental_backend.domain.Car;
import com.crudapp.car_rental_backend.domain.Rent;
import com.crudapp.car_rental_backend.domain.Segment;
import com.crudapp.car_rental_backend.domain.dto.CarDto;
import com.crudapp.car_rental_backend.domain.dto.NewCarDto;
import com.crudapp.car_rental_backend.repository.RentRepository;
import com.crudapp.car_rental_backend.repository.SegmentRepository;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class CarMapper {

    private SegmentRepository segmentRepository;
    private RentRepository rentRepository;

    public Car mapToCar(CarDto carDto) throws SegmentNotFoundException {
        return new Car(
                carDto.getCarId(),
                carDto.getModel(),
                carDto.getProductionYear(),
                carDto.getBasicPrice(),
                carDto.getCarStatus(),
                findSegment(carDto.getSegmentId()),
                findAllRentsById(carDto.getRentsId()));
    }

    public CarDto mapToCarDto(Car car) {
        return new CarDto(
                car.getCarId(),
                car.getModel(),
                car.getProductionYear(),
                car.getBasicPrice(),
                car.getCarStatus(),
                car.getSegment().getSegmentId(),
                mapToRentsId(car.getRents()));
    }

    public List<CarDto> mapToCarDtoList(List<Car> cars) {
        return cars.stream()
                .map(c -> new CarDto(
                        c.getCarId(),
                        c.getModel(),
                        c.getProductionYear(),
                        c.getBasicPrice(),
                        c.getCarStatus(),
                        c.getSegment().getSegmentId(),
                        mapToRentsId(c.getRents())
                ))
                .collect(Collectors.toList());
    }

    public Car mapToNewCar(NewCarDto newCarDto) throws SegmentNotFoundException {
        return new Car(
                newCarDto.getModel(),
                newCarDto.getProductionYear(),
                newCarDto.getBasicPrice(),
                newCarDto.getCarStatus(),
                segmentRepository.findById(newCarDto.getSegmentId()).orElseThrow(SegmentNotFoundException::new));
    }

    private Segment findSegment(Long segmentId) throws SegmentNotFoundException {
        return segmentRepository.findById(segmentId).orElseThrow(SegmentNotFoundException::new);
    }

    private List<Rent> findAllRentsById(List<Long> rentsId) {
        List<Rent> result = new ArrayList<>();
        if(Objects.nonNull(rentsId)) {
            for (Long id : rentsId) {
                rentRepository.findById(id).ifPresent(result::add);
            }
        }
        return result;
    }

    private List<Long> mapToRentsId(List<Rent> rents) {
        return rents.stream()
                .map(Rent::getRentId)
                .collect(Collectors.toList());
    }
}
