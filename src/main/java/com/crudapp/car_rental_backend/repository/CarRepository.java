package com.crudapp.car_rental_backend.repository;

import com.crudapp.car_rental_backend.domain.Car;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@Transactional
@Repository
public interface CarRepository extends CrudRepository<Car, Long> {

    @Override
    Optional<Car> findById(Long id);

    @Override
    List<Car> findAll();
    void deleteById(Long id);

    @Override
    Car save(Car car);

}
