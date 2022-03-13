package com.getir.readingIsGood.customer.service;

import com.getir.readingIsGood.common.authentication.payload.JwtResponse;
import com.getir.readingIsGood.common.authentication.payload.LoginRequest;
import com.getir.readingIsGood.common.authentication.payload.SignUpRequest;
import com.getir.readingIsGood.customer.dto.ResponseCustomerDto;
import com.getir.readingIsGood.order.dto.OrderDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface CustomerService {
    JwtResponse authenticateUser(LoginRequest loginRequest);
    ResponseCustomerDto registerUser(SignUpRequest signUpRequest);
    ResponseCustomerDto getCustomerInfo();
    Page<OrderDto> getAllOrdersOfCustomer(Pageable pageable);
}
