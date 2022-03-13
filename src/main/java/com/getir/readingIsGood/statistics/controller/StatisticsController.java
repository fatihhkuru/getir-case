package com.getir.readingIsGood.statistics.controller;


import com.getir.readingIsGood.order.dto.OrderDto;
import com.getir.readingIsGood.order.dto.ResponseOrderDto;
import com.getir.readingIsGood.statistics.dto.StatisticsDto;
import com.getir.readingIsGood.statistics.service.StatisticsService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@AllArgsConstructor
@RequestMapping("/api/statistics")
@RestController
public class StatisticsController {

    private final StatisticsService statisticsService;


    @GetMapping("/getMonthlyStatistics")
    public ResponseEntity<List<StatisticsDto>> getMonthlyStatistics(){
        List<StatisticsDto> responseOrderDto= statisticsService.getStatistics();
        return ResponseEntity.ok(responseOrderDto);
    }
}
