package com.crudapp.car_rental_backend.domain;

import com.crudapp.car_rental_backend.repository.CarRepository;
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
import static org.junit.Assert.*;
import static org.junit.jupiter.api.Assertions.assertEquals;

@RunWith(SpringRunner.class)
@Transactional
@SpringBootTest
public class SegmentTestSuite {

    @Autowired
    SegmentRepository segmentRepository;

    @Autowired
    CarRepository carRepository;

    @Test
    public void getSegmentsTest() {
        //GIVEN
        Segment segment1 = new Segment("Bus", "cars for big cargo");
        Segment segment2 = new Segment("Classic", "classic car");
        segmentRepository.save(segment1);
        segmentRepository.save(segment2);
        Long id1 = segment1.getSegmentId();
        Long id2 = segment2.getSegmentId();
        //WHEN
        List<Segment> allSegments = segmentRepository.findAll();
        //THEN
        assertEquals(6, allSegments.size());
        //CLEAN UP
        segmentRepository.deleteById(id1);
        segmentRepository.deleteById(id2);
    }

    @Test
    public void GetSegmentByITest() {
        //GIVEN
        Segment segment1 = new Segment("Bus", "cars for big cargo");
        segmentRepository.save(segment1);
        Long id1 = segment1.getSegmentId();
        //WHEN
        Segment segmentById1 = segmentRepository.findById(id1).orElse(new Segment());
        //THEN
        assertEquals(segment1.getSegmentName(), segmentById1.getSegmentName());
        assertEquals(segment1.getSegmentDescription(), segmentById1.getSegmentDescription());
        //CLEAN UP
        segmentRepository.deleteById(id1);
    }

    @Test
    public void createSegmentTest(){
        //GIVEN & WHEN
        Segment segment1 = new Segment("Classic", "Classic Car");
        segmentRepository.save(segment1);
        //THEN
        assertTrue(segmentRepository.findById(segment1.getSegmentId()).isPresent());
        //CLEAN UP
        segmentRepository.deleteAll();
    }

    @Test
    public void updateSegmentTest(){
        //GIVEN
        Segment segment1 = new Segment("Classic", "Classic Car");
        segmentRepository.save(segment1);

        //WHEN
        Long id1 = segment1.getSegmentId();
        Segment segmentById1 = segmentRepository.findById(id1).orElse(new Segment());
        segmentById1.setSegmentDescription("Classic American Car");
        segmentRepository.save(segmentById1);

        //THEN
        System.out.println(segmentRepository.findById(id1).get().getSegmentDescription());
        assertEquals("Classic American Car", segmentRepository.findById(id1).get().getSegmentDescription());
        //CLEANUP
        segmentRepository.deleteAll();
    }

    @Test
    public void deleteSegmentTest() {
        //GIVEN
        Segment segment1 = new Segment("Classic", "classic Car");
        segmentRepository.save(segment1);
        //WHEN
        Long id1 = segment1.getSegmentId();
        segmentRepository.deleteById(id1);
        //THEN
        assertEquals(4,segmentRepository.findAll().size());
        assertFalse(segmentRepository.findById(id1).isPresent());
        //CLEAN UP
        segmentRepository.deleteAll();

    }

    @Test
    public void addCarToSegmentTest() {
        //GIVEN
        Segment segment1 = new Segment("Classic", "classic Car");
        segmentRepository.save(segment1);

        Car car1 = new Car("Ford Mustang", 1971, new BigDecimal(70.00), FREE);
        Car car2 = new Car("Dodge Challenger", 1974, new BigDecimal(70.00), FREE);
        carRepository.save(car1);
        carRepository.save(car2);
        segment1.getCars().add(car1);
        segment1.getCars().add(car2);

        Long id1 = segment1.getSegmentId();

        //WHEN
        int testCarsInSegmentsSize = segment1.getCars().size();
        //THEN
        assertEquals(2, testCarsInSegmentsSize);
        //CLEAN UP
        segmentRepository.deleteById(id1);
        carRepository.deleteAll();

    }

}
