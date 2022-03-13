package com.getir.readingIsGood.customer.dto;

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
public class CustomerDto {
    @NotBlank
    private String email;
    @NotBlank
    private String userName;
    @NotBlank
    private String passWord;
}
