package com.getir.readingIsGood.order.controller;

import com.getir.readingIsGood.book.dto.BookDto;
import com.getir.readingIsGood.book.dto.ResponseBookDto;
import com.getir.readingIsGood.customer.service.CustomerService;
import com.getir.readingIsGood.order.dto.OrderDto;
import com.getir.readingIsGood.order.dto.ResponseOrderDto;
import com.getir.readingIsGood.order.service.OrderService;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.time.LocalDate;
import java.util.Date;

@AllArgsConstructor
@RequestMapping("/api/order")
@RestController
public class OrderController {

    private final OrderService orderService;

    @PostMapping("/addNewOrder")
    public ResponseEntity<ResponseOrderDto> addNewBook(@Valid @RequestBody OrderDto orderDto){
        ResponseOrderDto responseOrderDto= orderService.addNewOrder(orderDto);
        return ResponseEntity.ok(responseOrderDto);
    }

    @GetMapping("/{orderId}")
    public ResponseEntity<ResponseOrderDto> getOrderById(@PathVariable("orderId") String orderId) {
        ResponseOrderDto requestedOrder = orderService.findOrderById(orderId);
        return ResponseEntity.ok(requestedOrder);
    }

    @GetMapping
    public ResponseEntity<Page<ResponseOrderDto>> getOrdersByDateInterval(@Valid  @RequestParam(defaultValue = "01011970") @DateTimeFormat(pattern = "ddMMyyyy") LocalDate startDate, @RequestParam(required = false) @DateTimeFormat(pattern = "ddMMyyyy") LocalDate endDate, Pageable pageable) {
        Page<ResponseOrderDto> responseOrderDtos = orderService.findOrdersByCustomerIdAndDateInterval(startDate, endDate, pageable);
        return ResponseEntity.ok(responseOrderDtos);
    }

}
