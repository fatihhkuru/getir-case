package com.getir.readingIsGood.book.model;

import com.mongodb.lang.NonNull;
import lombok.*;
import org.hibernate.validator.constraints.NotBlank;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;

@Data
@Document(collection = "books")
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class Book {
    @Id
    private String id;
    @NotBlank
    private String name;
    @NotBlank
    private String author;
    @NotNull
    @Min(0)
    private Integer stock;
    @NotNull
    @Min(0)
    private BigDecimal price;
    private String description;
}
