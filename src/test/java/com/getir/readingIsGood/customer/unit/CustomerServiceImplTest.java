package com.getir.readingIsGood.customer.unit;

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
import com.getir.readingIsGood.customer.service.CustomerService;
import com.getir.readingIsGood.customer.service.CustomerServiceImpl;
import com.getir.readingIsGood.order.service.OrderService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.modelmapper.ModelMapper;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.ArrayList;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.mock;

class CustomerServiceImplTest {

    private CustomerService customerService;

    private CustomerRepository customerRepository;

    private AuthenticationManager authenticationManager;

    private UserDetailsService userDetailsService;

    private JwtUtils jwtUtils;

    PasswordEncoder encoder;

    private OrderService orderService;

    private ModelMapper modelMapper;

    private SecurityContext securityContext;


    LoginRequest loginRequest;
    SignUpRequest signUpRequest;
    JwtResponse jwtResponse;
    Authentication authentication;
    Customer customer;
    ResponseCustomerDto responseCustomerDto;
    UserDetails userDetails;
    Authentication auth;

    @BeforeEach
    public void setUp(){
         customerRepository = mock(CustomerRepository.class);
         authenticationManager = mock(AuthenticationManager.class);
         userDetailsService = mock(UserDetailsService.class);
         orderService = mock(OrderService.class);
         securityContext = Mockito.mock(SecurityContext.class);
         modelMapper = new ModelMapper();
         encoder = new BCryptPasswordEncoder();
         customerService = new CustomerServiceImpl(customerRepository, authenticationManager, userDetailsService,
                 jwtUtils, encoder, orderService, modelMapper);
         setupData();
    }

    public void setupData(){
        signUpRequest = new SignUpRequest("fatihk","fatihk@dd.com", "pass", ERole.ROLE_CUSTOMER);
        customer = new Customer("", signUpRequest.getEmail(), signUpRequest.getUsername(), "pass", ERole.ROLE_CUSTOMER);
        responseCustomerDto = new ResponseCustomerDto(null, signUpRequest.getEmail(), signUpRequest.getUsername());
        UserDetailsImpl userDetails = new UserDetailsImpl("622e43d18973b15c6b80a80a", "fatihkuru", "fatihkuru", encoder.encode("pass"), new ArrayList<SimpleGrantedAuthority>());
        auth = new UsernamePasswordAuthenticationToken(userDetails,null);
        securityContext.setAuthentication(auth);
    }

    @Test
    void testRegisterUser_whenUserIsNotExists_shouldReturnCustomer() {
        //When
        Mockito.when(customerRepository.existsByEmail(signUpRequest.getEmail())).thenReturn(Boolean.FALSE);
        Mockito.when(customerRepository.existsByUserName(signUpRequest.getUsername())).thenReturn(Boolean.FALSE);
        Mockito.when(customerRepository.save(customer)).thenReturn(customer);
        //then
        assertEquals(responseCustomerDto, customerService.registerUser(signUpRequest));
        Mockito.verify(customerRepository).existsByEmail(customer.getEmail());
        Mockito.verify(customerRepository).existsByUserName(customer.getUserName());
    }

    @Test
    void testRegisterUser_whenUserNameExists_shouldThrowUserNameIsAlreadyTakenException() {
        //given
        //When
        Mockito.when(customerRepository.existsByEmail(signUpRequest.getEmail())).thenReturn(Boolean.FALSE);
        Mockito.when(customerRepository.existsByUserName(signUpRequest.getUsername())).thenReturn(Boolean.TRUE);

        ReadingIsGoodException exception = assertThrows(ReadingIsGoodException.class, () -> customerService.registerUser(signUpRequest));
        assertEquals(ExceptionCode.USER_NAME_IS_ALREADY_TAKEN, exception.getExceptionCode());
    }

    @Test
    void testRegisterUser_whenEmailExists_shouldThrowEmailIsAlreadyTakenException() {
        //given
        //When
        Mockito.when(customerRepository.existsByEmail(signUpRequest.getEmail())).thenReturn(Boolean.TRUE);
        Mockito.when(customerRepository.existsByUserName(signUpRequest.getUsername())).thenReturn(Boolean.FALSE);

        ReadingIsGoodException exception = assertThrows(ReadingIsGoodException.class, () -> customerService.registerUser(signUpRequest));
        assertEquals(ExceptionCode.USER_EMAIL_IS_ALREADY_TAKEN, exception.getExceptionCode());
    }

}