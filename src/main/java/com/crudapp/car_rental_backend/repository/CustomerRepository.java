package com.crudapp.car_rental_backend.repository;

import com.crudapp.car_rental_backend.domain.Customer;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@Repository
@Transactional
public interface CustomerRepository extends CrudRepository<Customer, Long> {

    @Override
    Optional<Customer> findById(Long id);

    @Override
    List<Customer> findAll();
    void deleteById(Long id);

    @Override
    Customer save(Customer customer);

}
