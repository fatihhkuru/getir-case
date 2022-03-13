package com.getir.readingIsGood.order.service;

import com.getir.readingIsGood.order.dto.OrderDto;
import com.getir.readingIsGood.order.dto.ResponseOrderDto;
import com.getir.readingIsGood.order.model.Order;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.time.LocalDate;
import java.util.List;

public interface OrderService {
    ResponseOrderDto addNewOrder(OrderDto orderDto);
    Page<OrderDto> findAllByCustomerId(String customerId, Pageable pageable);
    List<Order> getAllOrdersByCustomerId(String customerId);
    ResponseOrderDto findOrderById(String orderId);
    Page<ResponseOrderDto> findOrdersByCustomerIdAndDateInterval(LocalDate startDate, LocalDate endDate, Pageable pageable);
}
