package com.getir.readingIsGood.customer.model;

import lombok.*;
import org.hibernate.validator.constraints.NotBlank;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@Document(collection = "customers")
@ToString
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Customer {
    @Id
    private String id;
    @NotBlank
    private String email;
    @NotBlank
    private String userName;
    @NotBlank
    private String password;
    private ERole role;
}
