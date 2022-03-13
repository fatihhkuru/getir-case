package com.getir.readingIsGood.order.dto;

import lombok.*;
import org.hibernate.validator.constraints.NotBlank;
import org.hibernate.validator.constraints.NotEmpty;
import org.springframework.data.annotation.Id;

import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class ResponseOrderDto {
    private String id;
    private String customerId;
    private List<OrderBook> orderBooks;
    private BigDecimal totalPrice;
}
