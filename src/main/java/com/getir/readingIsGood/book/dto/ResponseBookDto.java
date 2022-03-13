package com.getir.readingIsGood.book.dto;

import lombok.*;
import org.hibernate.validator.constraints.NotBlank;

import java.math.BigDecimal;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class ResponseBookDto {
    private String id;
    private String name;
    private String author;
    private Integer stock;
    private BigDecimal price;
    private String description;
}
