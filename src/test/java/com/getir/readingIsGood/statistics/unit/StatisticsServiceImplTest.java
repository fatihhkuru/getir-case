package com.getir.readingIsGood.statistics.unit;

import com.getir.readingIsGood.customer.dto.ResponseCustomerDto;
import com.getir.readingIsGood.customer.service.CustomerService;
import com.getir.readingIsGood.order.dto.OrderBook;
import com.getir.readingIsGood.order.model.Order;
import com.getir.readingIsGood.order.service.OrderService;
import com.getir.readingIsGood.statistics.dto.StatisticsDto;
import com.getir.readingIsGood.statistics.service.StatisticsService;
import com.getir.readingIsGood.statistics.service.StatisticsServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.mock;

class StatisticsServiceImplTest {

    private StatisticsService statisticsService;
    private OrderService orderService;
    private CustomerService customerService;

    @BeforeEach
    public void setUp(){
         orderService = mock(OrderService.class);
         customerService = mock(CustomerService.class);
         statisticsService = new StatisticsServiceImpl(orderService, customerService);
    }

    @Test
    void testGetStatistics_whenOrderListNotEmphty_shouldReturnNotEmpthyStatisDtoList() {
        //given
        List<OrderBook> orderBookListList =  Arrays.asList(OrderBook.builder().bookId("1").count(3).build(), OrderBook.builder().bookId("1").count(2).build());

        Order order1 = new Order();
        order1.setDate(LocalDate.now());
        order1.setOrderBooks(orderBookListList);
        order1.setTotalPrice(BigDecimal.valueOf(20));

        List<OrderBook> orderBookListList2 =  Arrays.asList(OrderBook.builder().bookId("1").count(3).build(), OrderBook.builder().bookId("1").count(2).build());
        Order order2 = new Order();
        order2.setDate(LocalDate.now());
        order2.setOrderBooks(orderBookListList2);
        order2.setTotalPrice(BigDecimal.valueOf(40));

        List<Order> orderList = new ArrayList<>();
        orderList.add(order1);
        orderList.add(order2);

        StatisticsDto statisticsDto = new StatisticsDto();
        statisticsDto.setTotalOrderCount(2);
        statisticsDto.setTotalPurchasedAmount(BigDecimal.valueOf(60.0));
        statisticsDto.setTotalBookCount(10);

        ResponseCustomerDto customer = new ResponseCustomerDto("id","dummy","dummy");
        //When
        Mockito.when(customerService.getCustomerInfo()).thenReturn(customer);
        Mockito.when(orderService.getAllOrdersByCustomerId(customer.getId())).thenReturn(orderList);
        //then
        assertEquals(statisticsDto.getTotalOrderCount(), statisticsService.getStatistics().get(0).getTotalOrderCount());
        assertEquals(statisticsDto.getTotalPurchasedAmount(), statisticsService.getStatistics().get(0).getTotalPurchasedAmount());
        assertEquals(statisticsDto.getTotalBookCount(), statisticsService.getStatistics().get(0).getTotalBookCount());
    }

    @Test
    void testGetStatistics_whenOrderListEmphty_shouldReturnEmpthyStatisDtoList() {
        //given
        List<Order> empthyOrderList = new ArrayList<>();
        List<StatisticsDto> emphtyStatisticsDtoList = new ArrayList<>();
        ResponseCustomerDto customer = new ResponseCustomerDto("id","dummy","dummy");
        //When
        Mockito.when(customerService.getCustomerInfo()).thenReturn(customer);
        Mockito.when(orderService.getAllOrdersByCustomerId(customer.getId())).thenReturn(empthyOrderList);
        //then
        assertEquals(emphtyStatisticsDtoList, statisticsService.getStatistics());
    }


}