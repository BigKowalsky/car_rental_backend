package com.crudapp.car_rental_backend.domain;

import com.crudapp.car_rental_backend.repository.CarRepository;
import com.crudapp.car_rental_backend.repository.RentRepository;
import com.crudapp.car_rental_backend.repository.SegmentRepository;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.transaction.Transactional;
import java.math.BigDecimal;
import java.util.List;

import static com.crudapp.car_rental_backend.domain.CarStatus.FREE;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.junit.jupiter.api.Assertions.assertEquals;

@RunWith(SpringRunner.class)
@Transactional
@SpringBootTest
public class CarTestSuite {

@Autowired
private CarRepository carRepository;

@Autowired
private SegmentRepository segmentRepository;

@Autowired
private RentRepository rentRepository;

    @Test
    public void getCarsTest() {
        //GIVEN
        Car car1 = new Car("Toyota Avensis", 2015, new BigDecimal(20.00), FREE);
        Car car2 = new Car("Honda Accord", 2014, new BigDecimal(20.00), FREE);
        Car car3 = new Car("Mazda 6", 2016, new BigDecimal(20.00), FREE);
        carRepository.save(car1);
        carRepository.save(car2);
        carRepository.save(car3);
        Long id1 = car1.getCarId();
        Long id2 = car2.getCarId();
        Long id3 = car3.getCarId();
        //WHEN
        List<Car> allCars = carRepository.findAll();
        //THEN
        assertEquals(15, allCars.size());
        //CLEAN UP
        carRepository.deleteById(id1);
        carRepository.deleteById(id2);
        carRepository.deleteById(id3);
    }

    @Test
    public void getCarTest() {
        //GIVEN
        Car car1 = new Car("Toyota Avensis", 2015, new BigDecimal(20.00), FREE);
        Car car2 = new Car("Honda Accord", 2014, new BigDecimal(20.00), FREE);
        carRepository.save(car1);
        carRepository.save(car2);
        Long id1 = car1.getCarId();
        Long id2 = car2.getCarId();
        //WHEN
        Car car1Id1 = carRepository.findById(id1).orElse(new Car());
        Car car2Id2 = carRepository.findById(id2).orElse(new Car());
        //THEN
        assertEquals(car1.getModel(), car1Id1.getModel());
        assertEquals(car2.getModel(), car2Id2.getModel());
        //CLEAN UP
        carRepository.deleteById(id1);
        carRepository.deleteById(id2);
    }

    @Test
    public void createCarTest(){
        //GIVEN
        Car car1 = new Car();
        Segment segment1 = new Segment("Sport", "Sport car");
        car1.setSegment(segment1);
        //WHEN
        carRepository.save(car1);
        segmentRepository.save(segment1);
        //THEN
        assertTrue(carRepository.findById(car1.getCarId()).isPresent());
        //CLEAN UP
        carRepository.deleteById(car1.getCarId());
        segmentRepository.deleteById(segment1.getSegmentId());
    }

    @Test
    public void updateCarTest(){
        //GIVEN
        Car car1 = new Car("Seat Ibiza", 2021, new BigDecimal(30.00), FREE);
        carRepository.save(car1);
        //WHEN
        Long id1 = car1.getCarId();
        Car updatedCar1 = carRepository.findById(id1).orElse(new Car());
        updatedCar1.setBasicPrice(new BigDecimal(45.00));
        carRepository.save(updatedCar1);
        //THEN
        System.out.println(carRepository.findById(id1).get().getBasicPrice());
        assertEquals(new BigDecimal(45.00), carRepository.findById(id1).get().getBasicPrice());
        //CLEANUP
        carRepository.deleteAll();
    }

    @Test
    public void deleteCarTest(){
        //GIVEN
        Car car1 = new Car("FSO Polonez", 1993, new BigDecimal(10.00), FREE);
        carRepository.save(car1);
        //WHEN
        Long id1 = car1.getCarId();
        carRepository.deleteById(id1);
        //THEN
        assertEquals(12,carRepository.findAll().size());
        assertFalse(carRepository.findById(id1).isPresent());
        //CLEAN UP
        carRepository.deleteAll();
    }

    @Test
    public void manyCarsInManyRentsTest(){
        //GIVEN
        Car car1 = new Car("Toyota Hilux", 2020, new BigDecimal(50.00), FREE);
        Car car2 = new Car("Dacia Duster", 2021, new BigDecimal(50.00), FREE);
        carRepository.save(car1);
        carRepository.save(car2);
        //WHEN
        Rent rent1 = new Rent();
        Rent rent2 = new Rent();
        rent1.getRentedCars().add(car1);
        rent1.getRentedCars().add(car2);
        rent2.getRentedCars().add(car1);
        rent2.getRentedCars().add(car2);
        rentRepository.save(rent1);
        rentRepository.save(rent2);
        //THEN
        int testCarsInRent1 = rent1.getRentedCars().size();
        int testCarsInRent2 = rent2.getRentedCars().size();
        assertEquals(2, testCarsInRent1);
        assertEquals(2, testCarsInRent2);
        //CLEANUP
        rentRepository.deleteAll();
        carRepository.deleteAll();
    }
}
