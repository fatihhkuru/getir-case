package com.getir.readingIsGood.order.repository;

import com.getir.readingIsGood.book.model.Book;
import com.getir.readingIsGood.order.model.Order;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface OrderRepository extends MongoRepository<Order, String> {
    Page<Order> findAllByCustomerId(String customerId, Pageable pageable);
    List<Order> findAllByCustomerId(String customerId);
    Page<Order> findAllByCustomerIdAndDateBetween(String customerId, LocalDate start, LocalDate end, Pageable pageable);
}
