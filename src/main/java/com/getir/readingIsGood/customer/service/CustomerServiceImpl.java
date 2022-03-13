package com.getir.readingIsGood.customer.service;

import com.getir.readingIsGood.book.service.BookServiceImpl;
import com.getir.readingIsGood.common.authentication.*;
import com.getir.readingIsGood.common.authentication.payload.JwtResponse;
import com.getir.readingIsGood.common.authentication.payload.LoginRequest;
import com.getir.readingIsGood.common.authentication.payload.SignUpRequest;
import com.getir.readingIsGood.common.authentication.utils.JwtUtils;
import com.getir.readingIsGood.common.exception.ExceptionCode;
import com.getir.readingIsGood.common.exception.ReadingIsGoodException;
import com.getir.readingIsGood.customer.dto.ResponseCustomerDto;
import com.getir.readingIsGood.customer.model.Customer;
import com.getir.readingIsGood.customer.model.ERole;
import com.getir.readingIsGood.customer.repository.CustomerRepository;
import com.getir.readingIsGood.order.dto.OrderDto;
import com.getir.readingIsGood.order.service.OrderService;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class CustomerServiceImpl implements CustomerService{

    private CustomerRepository customerRepository;

    private AuthenticationManager authenticationManager;

    private UserDetailsService userDetailsService;

    private JwtUtils jwtUtils;

    private final OrderService orderService;

    private final ModelMapper modelMapper;

    PasswordEncoder encoder;

    private final Logger logger = LoggerFactory.getLogger(CustomerServiceImpl.class);

    public CustomerServiceImpl(CustomerRepository customerRepository, AuthenticationManager authenticationManager, UserDetailsService userDetailsService, JwtUtils jwtUtils, PasswordEncoder encoder, OrderService orderService, ModelMapper modelMapper) {
        this.customerRepository = customerRepository;
        this.authenticationManager = authenticationManager;
        this.userDetailsService = userDetailsService;
        this.jwtUtils = jwtUtils;
        this.encoder = encoder;
        this.orderService = orderService;
        this.modelMapper = modelMapper;
    }

    @Override
    public JwtResponse authenticateUser(LoginRequest loginRequest) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(loginRequest.getUsername(), loginRequest.getPassword()));
        SecurityContextHolder.getContext().setAuthentication(authentication);
        String jwt = jwtUtils.generateJwtToken(authentication);

        UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();
        List<String> roles = userDetails.getAuthorities().stream()
                .map(item -> item.getAuthority())
                .collect(Collectors.toList());
        return new JwtResponse(jwt,
                userDetails.getId(),
                userDetails.getUsername(),
                userDetails.getEmail(),
                roles);    }

    @Override
    public ResponseCustomerDto registerUser(SignUpRequest signUpRequest) {
        if (customerRepository.existsByUserName(signUpRequest.getUsername()))
            throw new ReadingIsGoodException(signUpRequest.getEmail(), ExceptionCode.USER_NAME_IS_ALREADY_TAKEN);
        if (customerRepository.existsByEmail(signUpRequest.getEmail()))
            throw new ReadingIsGoodException(signUpRequest.getEmail(), ExceptionCode.USER_EMAIL_IS_ALREADY_TAKEN);

        // Create new user's account
        Customer customer = new Customer();
        customer.setEmail(signUpRequest.getEmail());
        customer.setUserName(signUpRequest.getUsername());
        customer.setPassword(encoder.encode(signUpRequest.getPassword()));
        ERole eRole = signUpRequest.getRoles();
        if (eRole == ERole.ROLE_ADMIN)
            customer.setRole(eRole);
        else
            customer.setRole(ERole.ROLE_CUSTOMER);
        customer.setRole(eRole);
        customerRepository.save(customer);
        logger.info("New user is registered and saved. CustomerName: " + customer.getUserName());
        return modelMapper.map(customer, ResponseCustomerDto.class);
    }

    @Override
    public ResponseCustomerDto getCustomerInfo() {
        UserDetails userDetails =
                (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        String username = userDetails.getUsername();
        Customer customer = customerRepository.findCustomerByUserName(username)
                .orElseThrow(()-> new ReadingIsGoodException(username, ExceptionCode.USER_NOT_FOUND));
        return modelMapper.map(customer, ResponseCustomerDto.class);
    }

    @Override
    public Page<OrderDto> getAllOrdersOfCustomer(Pageable pageable) {
        ResponseCustomerDto responseCustomerDto = getCustomerInfo();
        Customer customer = customerRepository.findCustomerByUserName(responseCustomerDto.getUserName())
                .orElseThrow(()-> new ReadingIsGoodException(responseCustomerDto.getUserName(), ExceptionCode.USER_NOT_FOUND));
        return orderService.findAllByCustomerId(customer.getId(),pageable);
    }


}
