package com.getir.readingIsGood.order.dto;

import lombok.*;
import org.hibernate.validator.constraints.NotBlank;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class OrderBook {
    @NotBlank
    private String bookId;
    @NotNull
    @Min(0)
    private Integer count;
}
