package com.getir.readingIsGood.order.unit;

import com.getir.readingIsGood.book.dto.ResponseBookDto;
import com.getir.readingIsGood.book.service.BookService;
import com.getir.readingIsGood.common.exception.ExceptionCode;
import com.getir.readingIsGood.common.exception.ReadingIsGoodException;
import com.getir.readingIsGood.customer.dto.ResponseCustomerDto;
import com.getir.readingIsGood.customer.service.CustomerService;
import com.getir.readingIsGood.order.dto.OrderBook;
import com.getir.readingIsGood.order.dto.OrderDto;
import com.getir.readingIsGood.order.dto.ResponseOrderDto;
import com.getir.readingIsGood.order.model.Order;
import com.getir.readingIsGood.order.repository.OrderRepository;
import com.getir.readingIsGood.order.service.OrderService;
import com.getir.readingIsGood.order.service.OrderServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.mock;

class OrderServiceImplTest {

    private OrderService orderService;
    private OrderRepository orderRepository;
    private CustomerService customerService;
    private BookService bookService;
    private ModelMapper modelMapper;

    ResponseCustomerDto responseCustomerDto;
    ResponseBookDto responseBookDto1;
    ResponseBookDto responseBookDto2;
    Order order;
    OrderDto orderDto;
    ResponseOrderDto responseOrderDto;


    @BeforeEach
    public void setUp(){
         orderRepository = mock(OrderRepository.class);
         customerService = mock(CustomerService.class);
         bookService = mock(BookService.class);
         modelMapper = new ModelMapper();
         orderService = new OrderServiceImpl(orderRepository, customerService, bookService, modelMapper);
         setupData();
    }

    public void setupData(){
        List<OrderBook> orderBookListList =  Arrays.asList(OrderBook.builder().bookId("1").count(3).build(), OrderBook.builder().bookId("1").count(2).build());

        orderDto = OrderDto.builder().localDate(LocalDate.now()).orderBooks(orderBookListList).price(BigDecimal.valueOf(20)).build();
        responseCustomerDto = new ResponseCustomerDto("2", "dummy", "dummy");
        responseBookDto1 = ResponseBookDto.builder().id("1").stock(10).price(BigDecimal.valueOf(2)).build();
        responseBookDto1 = ResponseBookDto.builder().id("2").stock(10).price(BigDecimal.valueOf(2)).build();
        responseOrderDto = ResponseOrderDto.builder().orderBooks(orderDto.getOrderBooks()).customerId(responseBookDto1.getId()).totalPrice(BigDecimal.valueOf(10)).build();
    }

    @Test
    void testAddNewOrder_whenStocksHasEnoughForBuying_shouldReturnOrderDto(){
        //when
        Mockito.when(customerService.getCustomerInfo()).thenReturn(responseCustomerDto);
        Mockito.when(bookService.getBookInfoById("1")).thenReturn(responseBookDto1);
        Mockito.when(bookService.getBookInfoById("2")).thenReturn(responseBookDto2);
        Mockito.when(orderRepository.save(order)).thenReturn(order);
        //then

        assertEquals(responseOrderDto,orderService.addNewOrder(orderDto));
    }


    @Test
    void testFindOrdersByCustomerIdAndDateInterval_whenOrdersHasEnoughNumbers_shouldReturnSameResult(){
        //given;
        LocalDate startDate = LocalDate.now();
        LocalDate endDate = LocalDate.now();
        PageRequest pageable = PageRequest.of(0, 10);

        List<Order> orderList = Arrays.asList(Order.builder().id("1").build(), Order.builder().id("2").build());
        Page<Order> pageOrderList = new PageImpl<>(orderList);

        //when
        Mockito.when(customerService.getCustomerInfo()).thenReturn(ResponseCustomerDto.builder().id("1").build());
        Mockito.when(orderRepository.findAllByCustomerIdAndDateBetween("1", startDate, endDate, pageable)).thenReturn(pageOrderList);
        //Than
        assertEquals(orderList.size(), orderService.findOrdersByCustomerIdAndDateInterval(startDate, endDate, pageable).getSize());
    }

    @Test
    void testFindOrderById_whenOrderExists_shouldReturnOrderDto(){
        //given;
        String orderId = String.valueOf('1');

        //when
        Mockito.when(orderRepository.findById(orderId)).thenReturn(Optional.of(Order.builder().id(orderId).build()));
        //Than
        assertEquals(ResponseOrderDto.builder().id(orderId).build(), orderService.findOrderById(orderId));
    }

    @Test
    void testFindOrderById_whenOrderIsNotExists_shouldThrowOrderNotExistsException(){
        //given;
        String orderId = String.valueOf('1');

        //when
        Mockito.when(orderRepository.findById(orderId)).thenReturn(Optional.empty());
        //Than
        ReadingIsGoodException exception = assertThrows(ReadingIsGoodException.class, () -> orderService.findOrderById(orderId));
        assertEquals(ExceptionCode.ORDER_NOT_FOUND, exception.getExceptionCode());
    }

}