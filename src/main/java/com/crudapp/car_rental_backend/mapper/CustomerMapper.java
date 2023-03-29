package com.crudapp.car_rental_backend.mapper;

import com.crudapp.car_rental_backend.domain.Customer;
import com.crudapp.car_rental_backend.domain.Rent;
import com.crudapp.car_rental_backend.domain.dto.CustomerDto;
import com.crudapp.car_rental_backend.repository.RentRepository;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class CustomerMapper {

    private RentRepository rentRepository;

    public Customer mapToCustomer(CustomerDto customerDto) {
        return new Customer(
                customerDto.getCustomerId(),
                customerDto.getCustomerName(),
                customerDto.getEmail(),
                findAllCustRentsById(customerDto.getRentsId())
        );
    }

    public CustomerDto mapToCustomerDto(Customer customer) {
        return new CustomerDto(
                customer.getCustomerId(),
                customer.getCustomerName(),
                customer.getEmail(),
                mapToRentsId(customer.getRents())
        );
    }


    public List<CustomerDto> mapToCustomerDtoList(List<Customer> customers) {
        return customers.stream()
                .map(c -> new CustomerDto(
                        c.getCustomerId(),
                        c.getCustomerName(),
                        c.getEmail(),
                        mapToRentsId(c.getRents())
                ))
                .collect(Collectors.toList());
    }

    public List<Rent> findAllCustRentsById(List<Long> rentsId) {
        List<Rent> result = new ArrayList<>();
        if (Objects.nonNull(rentsId)) {
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
