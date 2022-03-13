package com.getir.readingIsGood.customer.dto;

import lombok.*;
import org.hibernate.validator.constraints.NotBlank;

import java.math.BigDecimal;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class ResponseCustomerDto {
    private String id;
    private String email;
    private String userName;
}
