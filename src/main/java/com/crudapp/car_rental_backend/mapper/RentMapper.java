package com.crudapp.car_rental_backend.mapper;

import com.crudapp.car_rental_backend.controller.exceptions.CustomerNotFoundException;
import com.crudapp.car_rental_backend.domain.Car;
import com.crudapp.car_rental_backend.domain.Rent;
import com.crudapp.car_rental_backend.domain.dto.NewRentDto;
import com.crudapp.car_rental_backend.domain.dto.RentDto;
import com.crudapp.car_rental_backend.repository.CarRepository;
import com.crudapp.car_rental_backend.repository.CustomerRepository;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class RentMapper {

    private CarRepository carRepository;
    private CustomerRepository customerRepository;

    public Rent mapToRent(RentDto rentDto) throws CustomerNotFoundException {
        return new Rent(
                rentDto.getRentId(),
                rentDto.getRentStatus(),
                rentDto.getCreationDate(),
                findAllCarsById(rentDto.getCarsId()),
                customerRepository.findById(rentDto.getCustomerId()).orElseThrow(CustomerNotFoundException::new));
    }

    public RentDto mapToRentDto(Rent rent) {
        return new RentDto(
                rent.getRentId(),
                rent.getRentStatus(),
                rent.getCreationDate(),
                rent.getCustomer().getCustomerId(),
                rent.getRentedCars().isEmpty() ? Collections.emptyList() : rent.getRentedCars().stream()
                        .map(Car::getCarId)
                        .collect(Collectors.toList())
        );
    }

    public List<RentDto> mapToRentDtoList(List<Rent> rents) {
        return rents.stream()
                .map(r -> new RentDto(
                        r.getRentId(),
                        r.getRentStatus(),
                        r.getCreationDate(),
                        r.getCustomer().getCustomerId(),
                        r.getRentedCars().isEmpty() ? Collections.emptyList() : r.getRentedCars().stream()
                                .map(Car::getCarId)
                                .collect(Collectors.toList())
                ))
                .collect(Collectors.toList());
    }

    public Rent mapToNewRent(NewRentDto newRentDto) throws CustomerNotFoundException {
        return new Rent(
                newRentDto.getRentStatus(),
                newRentDto.getCreationDate(),
                findAllCarsById(newRentDto.getCarsId()),
                customerRepository.findById(newRentDto.getCustomerId()).orElseThrow(CustomerNotFoundException::new));
    }

    public  List<Car> findAllCarsById(List<Long> carsId) {
        List<Car> result = new ArrayList<>();
        if (Objects.nonNull(carsId)) {
            for (Long id : carsId) {
                carRepository.findById(id).ifPresent(result::add);
            }
        }
        return result;
    }
}
