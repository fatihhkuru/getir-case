package com.getir.readingIsGood.book.dto;

import lombok.*;
import org.hibernate.validator.constraints.NotBlank;
import org.springframework.data.annotation.Id;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class BookDto {
    @NotBlank
    private String name;
    @NotBlank
    private String author;
    @Min(0)
    @NotNull
    private Integer stock;
    @Min(0)
    @NotNull
    private BigDecimal price;
    private String description;
}
