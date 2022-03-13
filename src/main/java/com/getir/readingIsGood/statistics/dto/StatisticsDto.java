package com.getir.readingIsGood.statistics.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Setter
public class StatisticsDto {
    private String monthName;
    private Integer totalOrderCount;
    private Integer totalBookCount;
    private BigDecimal totalPurchasedAmount;
}
