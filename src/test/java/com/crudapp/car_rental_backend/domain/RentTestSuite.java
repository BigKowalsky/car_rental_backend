package com.crudapp.car_rental_backend.domain;

import com.crudapp.car_rental_backend.repository.CarRepository;
import com.crudapp.car_rental_backend.repository.CustomerRepository;
import com.crudapp.car_rental_backend.repository.RentRepository;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.transaction.Transactional;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

import static com.crudapp.car_rental_backend.domain.CarStatus.FREE;
import static com.crudapp.car_rental_backend.domain.RentStatus.CREATED;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.junit.jupiter.api.Assertions.assertEquals;

@RunWith(SpringRunner.class)
@Transactional
@SpringBootTest
public class RentTestSuite {

    @Autowired
    RentRepository rentRepository;

    @Autowired
    CarRepository carRepository;

    @Autowired
    CustomerRepository customerRepository;

    @Test
    public void getRentsTest() {
        //GIVEN
        Rent rent1 = new Rent();
        Rent rent2 = new Rent();
        Rent rent3 = new Rent();
        rentRepository.save(rent1);
        rentRepository.save(rent2);
        rentRepository.save(rent3);
        Long id1 = rent1.getRentId();
        Long id2 = rent2.getRentId();
        Long id3 = rent3.getRentId();
        //WHEN
        List<Rent> allRents = rentRepository.findAll();
        //THEN
        assertEquals(3, allRents.size());
        //CLEAN UP
        rentRepository.deleteById(id1);
        rentRepository.deleteById(id2);
        rentRepository.deleteById(id3);
    }

    @Test
    public void getRentTest() {
        //GIVEN
        Rent rent1 = new Rent();
        Rent rent2 = new Rent();
        rentRepository.save(rent1);
        rentRepository.save(rent2);
        Long id1 = rent1.getRentId();
        Long id2 = rent2.getRentId();
        //WHEN
        Rent rent1Id1 = rentRepository.findById(id1).orElse(new Rent());
        Rent rent2Id2 = rentRepository.findById(id2).orElse(new Rent());
        //THEN
        assertEquals(rent1.getRentId(), rent1Id1.getRentId());
        assertEquals(rent2.getRentId(), rent2Id2.getRentId());
        //CLEAN UP
        rentRepository.deleteById(id1);
        rentRepository.deleteById(id2);
    }

    @Test
    public void createRentTest() {
        //GIVEN
        Rent rent1 = new Rent();
        Customer customer1 = new Customer("Customer 1", "customer1@test.pl");
        rent1.setCustomer(customer1);

        //WHEN
        customerRepository.save(customer1);
        rentRepository.save(rent1);

        //THEN
        assertTrue(rentRepository.findById(rent1.getRentId()).isPresent());

        //CLEAN UP
        rentRepository.deleteById(rent1.getRentId());
        customerRepository.deleteById(customer1.getCustomerId());
    }

    @Test
    public void updateRentTest(){
        //GIVEN
        Rent rent1 = new Rent(CREATED, LocalDate.of(2023, 02, 15));
        rentRepository.save(rent1);
        //WHEN
        Long id1 = rent1.getRentId();
        Rent updatedRent1 = rentRepository.findById(id1).orElse(new Rent());
        updatedRent1.setCreationDate(LocalDate.of(2023,03,27));
        rentRepository.save(updatedRent1);
        //THEN
        System.out.println(rentRepository.findById(id1).get().getCreationDate());
        assertEquals(LocalDate.of(2023,03,27), rentRepository.findById(id1).get().getCreationDate());
        //CLEANUP
        rentRepository.deleteAll();
    }

    @Test
    public void deleteRentTest(){
        //GIVEN
        Rent rent1 = new Rent();
        rentRepository.save(rent1);
        //WHEN
        Long id1 = rent1.getRentId();
        rentRepository.deleteById(id1);
        //THEN
        assertEquals(0,rentRepository.findAll().size());
        assertFalse(rentRepository.findById(id1).isPresent());
        //CLEAN UP
        rentRepository.deleteAll();
    }

    @Test
    public void manyRentsWithManyCarsTest(){
        //GIVEN
        Rent rent1 = new Rent();
        Rent rent2 = new Rent();
        rentRepository.save(rent1);
        rentRepository.save(rent2);
        //WHEN
        Car car1 = new Car();
        Car car2 = new Car();
        car1.getRents().add(rent1);
        car1.getRents().add(rent2);
        car2.getRents().add(rent1);
        car2.getRents().add(rent2);
        carRepository.save(car1);
        carRepository.save(car2);
        //THEN
        int testRentsWithCars1 = car1.getRents().size();
        int testRentsWithCars2 = car2.getRents().size();
        assertEquals(2, testRentsWithCars1);
        assertEquals(2, testRentsWithCars2);
        //CLEANUP
        carRepository.deleteAll();
        rentRepository.deleteAll();
    }
}

