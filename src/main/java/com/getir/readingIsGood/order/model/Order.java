package com.getir.readingIsGood.order.model;

import com.getir.readingIsGood.order.dto.OrderBook;
import lombok.*;
import org.hibernate.validator.constraints.NotBlank;
import org.hibernate.validator.constraints.NotEmpty;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;

@Data
@Document(collection = "orders")
@ToString
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Order {
    @Id
    private String id;
    @NotBlank
    private String customerId;
    @NotEmpty
    private List<OrderBook> orderBooks;
    @NotNull
    private BigDecimal totalPrice;
    @NotNull
    private LocalDate date;

    public String getMonthOfDate(){
        return this.getDate().getMonth().toString();
    }
}
