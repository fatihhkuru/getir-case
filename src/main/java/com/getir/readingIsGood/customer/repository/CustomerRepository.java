package com.getir.readingIsGood.customer.repository;

import com.getir.readingIsGood.book.model.Book;
import com.getir.readingIsGood.customer.model.Customer;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CustomerRepository extends MongoRepository<Customer, String> {
    Optional<Customer> findCustomerByEmail(String email);
    Optional<Customer> findCustomerByUserName(String userName);
    Boolean existsByUserName(String userName);
    Boolean existsByEmail(String email);

}
