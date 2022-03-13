package com.getir.readingIsGood.common.authentication.payload;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.validator.constraints.NotBlank;

import java.io.Serializable;

@AllArgsConstructor
@Getter
@Setter
@NoArgsConstructor
public class LoginRequest implements Serializable {
    @NotBlank
    private String username;
    @NotBlank
    private String password;
}
