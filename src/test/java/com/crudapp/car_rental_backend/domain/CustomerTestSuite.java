package com.crudapp.car_rental_backend.domain;

import com.crudapp.car_rental_backend.repository.RentRepository;
import com.crudapp.car_rental_backend.repository.CustomerRepository;
import org.junit.Assert;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.transaction.Transactional;

import java.util.List;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.junit.jupiter.api.Assertions.assertEquals;

@RunWith(SpringRunner.class)
@Transactional
@SpringBootTest
public class CustomerTestSuite {

    @Autowired
    CustomerRepository customerRepository;

    @Autowired
    RentRepository rentRepository;

    @Test
    public void getCustomersTest() {
        //GIVEN
        Customer customer1 = new Customer("Test Customer 1", "test1@test.pl");
        Customer customer2 = new Customer("Test Customer 2", "test2@test.pl");
        customerRepository.save(customer1);
        customerRepository.save(customer2);
        Long id1 = customer1.getCustomerId();
        Long id2 = customer2.getCustomerId();

        //WHEN
        List<Customer> allCustomers = customerRepository.findAll();

        //THEN
        Assert.assertEquals(7, allCustomers.size());
        //CLEAN UP
        customerRepository.deleteById(id1);
        customerRepository.deleteById(id2);
    }

    @Test
    public void getCustomerByIdTest() {
        //GIVEN
        Customer customer1 = new Customer("Test Customer 1", "test1@test.pl");
        Customer customer2 = new Customer("Test Customer 2", "test2@test.pl");
        customerRepository.save(customer1);
        customerRepository.save(customer2);
        Long id1 = customer1.getCustomerId();
        Long id2 = customer2.getCustomerId();
        //WHEN
        Customer customer1Id1 = customerRepository.findById(id1).orElse(new Customer());
        Customer customer2Id2 = customerRepository.findById(id2).orElse(new Customer());
        //THEN
        assertEquals(customer1.getCustomerName(), customer1Id1.getCustomerName());
        assertEquals(customer2.getCustomerName(), customer2Id2.getCustomerName());
        //CLEAN UP
        customerRepository.deleteById(id1);
        customerRepository.deleteById(id2);
    }

    @Test
    public void createCustomerTest(){
        //GIVEN & WHEN
        Customer customer1 = new Customer();
        customerRepository.save(customer1);
        //THEN
        assertTrue(customerRepository.findById(customer1.getCustomerId()).isPresent());
        //CLEAN UP
        customerRepository.deleteById(customer1.getCustomerId());
    }

    @Test
    public void deleteCustomerTest(){
        //GIVEN
        Customer customer1 = new Customer("Test customer 1", "test1@test.pl");
        customerRepository.save(customer1);
        //WHEN
        Long id1 = customer1.getCustomerId();
        customerRepository.deleteById(id1);
        //THEN
        assertEquals(5,customerRepository.findAll().size());
        assertFalse(customerRepository.findById(id1).isPresent());
        //CLEAN UP
        customerRepository.deleteAll();
    }

    @Test
    public void updateCustomerTest(){
        //GIVEN
        Customer customer1 = new Customer("Customer XYZ", "test1@test.pl");
        customerRepository.save(customer1);
        //WHEN
        Long id1 = customer1.getCustomerId();
        Customer updatedCustomer1 = customerRepository.findById(id1).orElse(new Customer());
        updatedCustomer1.setEmail("testXYZ@test.pl");
        customerRepository.save(updatedCustomer1);
        //THEN
        System.out.println(customerRepository.findById(id1).get().getEmail());
        assertEquals("testXYZ@test.pl", customerRepository.findById(id1).get().getEmail());
        //CLEANUP
        customerRepository.deleteAll();
    }

    @Test
    public void addRentsToCustomerTest() {
        //GIVEN
        Customer customer1 = new Customer("Test Customer", "test@test.pl");
        customerRepository.save(customer1);
        Rent rent1 = new Rent();
        Rent rent2 = new Rent();
        Rent rent3 = new Rent();
        rentRepository.save(rent1);
        rentRepository.save(rent2);
        rentRepository.save(rent3);
        customer1.getRents().add(rent1);
        customer1.getRents().add(rent2);
        customer1.getRents().add(rent3);
        Long id1 = customer1.getCustomerId();
        //WHEN
        int oneCustomerManyRents = customer1.getRents().size();
        //THEN
        assertEquals(3, oneCustomerManyRents);
        //CLEAN UP
        customerRepository.deleteById(id1);
        rentRepository.deleteAll();
    }

}
