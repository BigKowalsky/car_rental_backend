package com.crudapp.car_rental_backend.service;

import com.crudapp.car_rental_backend.controller.exceptions.CustomerNotFoundException;
import com.crudapp.car_rental_backend.domain.Customer;
import com.crudapp.car_rental_backend.repository.CustomerRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Transactional
@Service
@RequiredArgsConstructor
public class CustomerService {

    private final CustomerRepository customerRepository;

    public List<Customer> getAllCustomers() {
        return customerRepository.findAll();
    }

    public Customer getCustomerById(Long customerId) throws CustomerNotFoundException {
        return customerRepository.findById(customerId).orElseThrow(CustomerNotFoundException::new);
    }

    public Customer createCustomer(final Customer customer) {
        customerRepository.save(customer);
        return customer;
    }

    public Customer updateCustomer(Customer customer) throws CustomerNotFoundException {
        Customer updatedCustomer = getCustomerById(customer.getCustomerId());
        updatedCustomer.setCustomerName(customer.getCustomerName());
        updatedCustomer.setEmail(customer.getEmail());
        updatedCustomer.setRents(customer.getRents());
        customerRepository.save(updatedCustomer);
        return updatedCustomer;
    }

    public void deleteCustomer(long customerId) {
        customerRepository.deleteById(customerId);
    }
}
