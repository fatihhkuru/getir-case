package com.getir.readingIsGood.statistics.service;

import com.getir.readingIsGood.statistics.dto.StatisticsDto;

import java.util.List;

public interface StatisticsService {
    List<StatisticsDto> getStatistics();
}
