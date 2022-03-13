package com.getir.readingIsGood.statistics.service;

import com.getir.readingIsGood.customer.dto.ResponseCustomerDto;
import com.getir.readingIsGood.customer.model.Customer;
import com.getir.readingIsGood.customer.service.CustomerService;
import com.getir.readingIsGood.order.dto.OrderBook;
import com.getir.readingIsGood.order.model.Order;
import com.getir.readingIsGood.order.service.OrderService;
import com.getir.readingIsGood.statistics.dto.StatisticsDto;

import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class StatisticsServiceImpl implements StatisticsService{

    private final OrderService orderService;
    private final CustomerService customerService;

    public StatisticsServiceImpl(OrderService orderService, CustomerService customerService) {
        this.orderService = orderService;
        this.customerService = customerService;
    }

    @Override
    public List<StatisticsDto> getStatistics() {
        ResponseCustomerDto customer = customerService.getCustomerInfo();
        List<Order> orderList = orderService.getAllOrdersByCustomerId(customer.getId());
        Map<String, List<Order>> monthMap = orderList.stream().collect(Collectors.groupingBy(Order::getMonthOfDate));
        List<StatisticsDto> statisticsDtoList = new ArrayList<>();
        for(String key : monthMap.keySet()){
            List<Order> orderListValues = monthMap.get(key);
            Integer totalOrderCount = orderListValues.size();
            Integer totalBookCount = 0;
            BigDecimal totalPurchasedAmount = BigDecimal.valueOf(0.0);
            for(Order order : orderListValues){
                totalBookCount += order.getOrderBooks().stream().map(OrderBook::getCount).reduce(0, Integer::sum);
                totalPurchasedAmount = totalPurchasedAmount.add(order.getTotalPrice());

            }
            StatisticsDto statisticsDto = new StatisticsDto(key, totalOrderCount, totalBookCount, totalPurchasedAmount);
            statisticsDtoList.add(statisticsDto);
        }
        return statisticsDtoList;
    }

}
