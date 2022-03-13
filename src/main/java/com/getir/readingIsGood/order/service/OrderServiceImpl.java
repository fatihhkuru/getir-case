package com.getir.readingIsGood.order.service;

import com.getir.readingIsGood.book.dto.BookDto;
import com.getir.readingIsGood.book.dto.ResponseBookDto;
import com.getir.readingIsGood.book.service.BookService;
import com.getir.readingIsGood.book.service.BookServiceImpl;
import com.getir.readingIsGood.common.exception.ExceptionCode;
import com.getir.readingIsGood.common.exception.ReadingIsGoodException;
import com.getir.readingIsGood.customer.dto.ResponseCustomerDto;
import com.getir.readingIsGood.customer.service.CustomerService;
import com.getir.readingIsGood.order.dto.OrderBook;
import com.getir.readingIsGood.order.dto.OrderDto;
import com.getir.readingIsGood.order.dto.ResponseOrderDto;
import com.getir.readingIsGood.order.model.Order;
import com.getir.readingIsGood.order.repository.OrderRepository;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Lazy;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Service
public class OrderServiceImpl implements OrderService {

    private final OrderRepository orderRepository;
    private final CustomerService customerService;
    private final BookService bookService;
    private final ModelMapper modelMapper;
    private final Logger logger = LoggerFactory.getLogger(OrderServiceImpl.class);


    public OrderServiceImpl(OrderRepository orderRepository, @Lazy CustomerService customerService, BookService bookService, ModelMapper modelMapper) {
        this.orderRepository = orderRepository;
        this.customerService = customerService;
        this.bookService = bookService;
        this.modelMapper = modelMapper;
    }

    @Override
    @Transactional(rollbackFor=Exception.class)
    public ResponseOrderDto addNewOrder(OrderDto orderDto) {
        BigDecimal totalPrice = new BigDecimal(0);
        ResponseCustomerDto responseCustomerDto = customerService.getCustomerInfo();
        for (OrderBook orderBook : orderDto.getOrderBooks()){
            ResponseBookDto responseBookDto = bookService.getBookInfoById(orderBook.getBookId());
            Integer newStock = responseBookDto.getStock() - orderBook.getCount();
            responseBookDto.setStock(newStock);
            bookService.updateBookStock(responseBookDto.getId(), modelMapper.map(responseBookDto, BookDto.class));
            BigDecimal sum = responseBookDto.getPrice().multiply(new BigDecimal(orderBook.getCount()));
            totalPrice = totalPrice.add(sum);
        }

        Order order = new Order();
        order.setDate(LocalDate.now());
        order.setOrderBooks(orderDto.getOrderBooks());
        order.setCustomerId(responseCustomerDto.getId());
        order.setTotalPrice(totalPrice);
        orderRepository.save(order);
        logger.info("New order is scheduled" + orderDto.toString());
        return modelMapper.map(order, ResponseOrderDto.class);
    }

    @Override
    public Page<ResponseOrderDto> findOrdersByCustomerIdAndDateInterval(LocalDate startDate, LocalDate endDate, Pageable pageable) {
        ResponseCustomerDto responseCustomerDto = customerService.getCustomerInfo();
        Page<Order> page = orderRepository.findAllByCustomerIdAndDateBetween(responseCustomerDto.getId(), startDate, endDate, pageable);
        return page.map(order -> modelMapper.map(order, ResponseOrderDto.class));
    }

    @Override
    public ResponseOrderDto findOrderById(String orderId) {
        Order order = orderRepository.findById(orderId).orElseThrow(()-> new ReadingIsGoodException(orderId, ExceptionCode.ORDER_NOT_FOUND));
        return modelMapper.map(order, ResponseOrderDto.class);
    }

    @Override
    public Page<OrderDto> findAllByCustomerId(String customerId, Pageable pageable) {
        Page<Order> page = orderRepository.findAllByCustomerId(customerId, pageable);
        return page.map(order -> modelMapper.map(order, OrderDto.class));
    }

    @Override
    public List<Order> getAllOrdersByCustomerId(String customerId) {
        return orderRepository.findAllByCustomerId(customerId);
    }
}
