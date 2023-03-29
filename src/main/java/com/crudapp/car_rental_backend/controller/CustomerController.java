package com.crudapp.car_rental_backend.controller;

import com.crudapp.car_rental_backend.controller.exceptions.CustomerNotFoundException;
import com.crudapp.car_rental_backend.controller.exceptions.RentNotFoundException;
import com.crudapp.car_rental_backend.domain.Customer;
import com.crudapp.car_rental_backend.domain.dto.*;
import com.crudapp.car_rental_backend.mapper.CustomerMapper;
import com.crudapp.car_rental_backend.service.CustomerService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/v1/customer")
public class CustomerController {

    private final CustomerMapper customerMapper;

    private final CustomerService customerService;

    @GetMapping
    public ResponseEntity<List<CustomerDto>> getAllCustomers() {
        return ResponseEntity.ok(customerMapper.mapToCustomerDtoList(customerService.getAllCustomers()));
    }

    @GetMapping(value = "{customerId}")
    public ResponseEntity<CustomerDto> getCustomer(@PathVariable Long customerId) throws CustomerNotFoundException {
        return ResponseEntity.ok(customerMapper.mapToCustomerDto(customerService.getCustomerById(customerId)));
    }

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Void> createCustomer(@RequestBody CustomerDto CustomerDto) throws RentNotFoundException {
        Customer customer = customerMapper.mapToCustomer(CustomerDto);
        customerService.createCustomer(customer);
        return ResponseEntity.ok().build();
    }

    @PutMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<CustomerDto> updateCustomer(@RequestBody CustomerDto customerDto) throws CustomerNotFoundException, RentNotFoundException {
        return ResponseEntity.ok(customerMapper.mapToCustomerDto(customerService.updateCustomer(customerMapper.mapToCustomer(customerDto))));
    }

    @DeleteMapping(value = "{customerId}")
    public ResponseEntity<Void> deleteUser(@PathVariable long customerId) {
        customerService.deleteCustomer(customerId);
        return ResponseEntity.ok().build();
    }
}
