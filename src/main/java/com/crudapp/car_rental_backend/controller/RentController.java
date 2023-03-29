package com.crudapp.car_rental_backend.controller;

import com.crudapp.car_rental_backend.controller.exceptions.CustomerNotFoundException;
import com.crudapp.car_rental_backend.controller.exceptions.RentNotFoundException;
import com.crudapp.car_rental_backend.domain.Customer;
import com.crudapp.car_rental_backend.domain.Rent;
import com.crudapp.car_rental_backend.domain.dto.NewRentDto;
import com.crudapp.car_rental_backend.domain.dto.RentDto;
import com.crudapp.car_rental_backend.mapper.RentMapper;
import com.crudapp.car_rental_backend.service.CustomerService;
import com.crudapp.car_rental_backend.service.RentService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/v1/rent")
public class RentController {

    private final RentMapper rentMapper;
    private final RentService rentService;
    private final CustomerService customerService;

    @GetMapping
    public ResponseEntity<List<RentDto>> getAllRents() {
        return ResponseEntity.ok(rentMapper.mapToRentDtoList(rentService.getAllRents()));
    }

    @GetMapping(value = "{customerId}")
    public ResponseEntity<List<RentDto>> getAllCustomersRents(@PathVariable Long customerId) throws CustomerNotFoundException {
        Customer customer = customerService.getCustomerById(customerId);
        List<Rent> rents = customer.getRents();
        return ResponseEntity.ok(rentMapper.mapToRentDtoList(rents));
    }

    @GetMapping(value = "{rentId}")
    public ResponseEntity<RentDto> getRent(@PathVariable Long rentId) throws RentNotFoundException {
        return ResponseEntity.ok(rentMapper.mapToRentDto(rentService.getRentById(rentId)));
    }

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Void> createRent(@RequestBody NewRentDto newRentDto) throws CustomerNotFoundException {
        Rent rent = rentMapper.mapToNewRent(newRentDto);
        rentService.createRent(rent);
        return ResponseEntity.ok().build();
    }

    @PutMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<RentDto> updateRent(@RequestBody RentDto rentDto) throws RentNotFoundException, CustomerNotFoundException {
        return ResponseEntity.ok(rentMapper.mapToRentDto(rentService.updateRent(rentMapper.mapToRent(rentDto))));
    }

    @DeleteMapping(value = "{rentId}")
    public ResponseEntity<Void> deleteRent(@PathVariable Long rentId) {
        rentService.deleteRent(rentId);
        return ResponseEntity.ok().build();
    }
}
