package com.getir.readingIsGood.customer.controller;

import com.getir.readingIsGood.common.authentication.payload.JwtResponse;
import com.getir.readingIsGood.common.authentication.payload.LoginRequest;
import com.getir.readingIsGood.common.authentication.payload.SignUpRequest;
import com.getir.readingIsGood.customer.dto.ResponseCustomerDto;
import com.getir.readingIsGood.customer.service.CustomerService;
import com.getir.readingIsGood.order.dto.OrderDto;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@AllArgsConstructor
@RequestMapping("/api/customer")
@RestController
public class CustomerController {

    private final CustomerService customerService;

    @PostMapping("/auth/signin")
    public ResponseEntity<JwtResponse> authenticateUser(@Valid @RequestBody LoginRequest loginRequest) throws Exception {
        JwtResponse jwtResponse = customerService.authenticateUser(loginRequest);
        return ResponseEntity.ok(jwtResponse);
    }

    @PostMapping("/auth/signup")
    public ResponseEntity<ResponseCustomerDto> registerUser(@Valid @RequestBody SignUpRequest signUpRequest) {
        ResponseCustomerDto responseCustomerDto = customerService.registerUser(signUpRequest);
        return ResponseEntity.ok(responseCustomerDto);
    }

    @GetMapping("/getAllOrders")
    public ResponseEntity<Page<OrderDto>> getAllOrders(Pageable pageable) {
        Page<OrderDto> page = customerService.getAllOrdersOfCustomer(pageable);
        return ResponseEntity.ok(page);
    }

    @GetMapping("/info")
    public ResponseEntity<ResponseCustomerDto> getCustomerInfo() {
        ResponseCustomerDto responseCustomerDto = customerService.getCustomerInfo();
        return ResponseEntity.ok(responseCustomerDto);
    }
}
