package com.crudapp.car_rental_backend.repository;

import com.crudapp.car_rental_backend.domain.Rent;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@Repository
@Transactional
public interface RentRepository extends CrudRepository<Rent, Long> {

    @Override
    Optional<Rent> findById(Long id);

    List<Rent> findAll();
    void deleteById(Long id);

    @Override
    Rent save(Rent rent);
}
