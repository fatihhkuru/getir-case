package com.getir.readingIsGood.customer.integration.repository;

import com.getir.readingIsGood.customer.model.ERole;
import com.getir.readingIsGood.customer.model.Customer;
import com.getir.readingIsGood.customer.repository.CustomerRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
public class CustomerRepositoryTest {

    @Autowired
    private CustomerRepository customerRepository;

    Customer customer;


    @BeforeEach
    public void setUp(){
        customer = new Customer("id","dummy@gmail.com", "userName", "dummyPass", ERole.ROLE_CUSTOMER);
    }

    @AfterEach
    void tearDown() {
        Optional<Customer> addedCustomer = customerRepository.findCustomerByUserName(customer.getUserName());
        customerRepository.deleteById(addedCustomer.get().getId());
    }

    @Test
    public void testFindCustomerByEmail() {
        //Given
        customerRepository.save(customer);
        Optional<Customer> addedCustomerOptional = customerRepository.findCustomerByEmail(customer.getUserName());
        Customer addedCustomer = addedCustomerOptional.get();
        //Then
        assertEquals(customer.getUserName(), addedCustomer.getUserName());
        assertEquals(customer.getEmail(), addedCustomer.getEmail());
        assertEquals(customer.getRole(), addedCustomer.getRole());
    }

    @Test
    public void testFindCustomerByUserName() {
        //Given
        customerRepository.save(customer);
        Optional<Customer> addedCustomerOptional = customerRepository.findCustomerByUserName(customer.getUserName());
        Customer addedCustomer = addedCustomerOptional.get();
        //Then
        assertEquals(customer.getUserName(), addedCustomer.getUserName());
        assertEquals(customer.getEmail(), addedCustomer.getEmail());
        assertEquals(customer.getRole(), addedCustomer.getRole());
    }

    @Test
    public void testExistsByUserName() {
        //Given
        customerRepository.save(customer);
        Boolean existsByUserName = customerRepository.existsByUserName(customer.getUserName());
        //Then
        assertEquals(Boolean.TRUE, existsByUserName);

        //Given
        Boolean notExistsByUser = customerRepository.existsByEmail(customer.getUserName()+"dummy");
        //Then
        assertEquals(Boolean.FALSE, notExistsByUser);
    }

    @Test
    public void testexistsByEmail() {
        //Given
        customerRepository.save(customer);
        Boolean existsByEmail = customerRepository.existsByEmail(customer.getEmail());
        //Then
        assertEquals(Boolean.TRUE, existsByEmail);

        //Given
        Boolean notExistsByEmail = customerRepository.existsByEmail(customer.getEmail()+"dummy");
        //Then
        assertEquals(Boolean.FALSE, notExistsByEmail);
    }
}
