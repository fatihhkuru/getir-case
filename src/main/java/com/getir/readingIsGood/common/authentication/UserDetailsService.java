package com.getir.readingIsGood.common.authentication;

import com.getir.readingIsGood.common.exception.ExceptionCode;
import com.getir.readingIsGood.common.exception.ReadingIsGoodException;
import com.getir.readingIsGood.customer.model.Customer;
import com.getir.readingIsGood.customer.repository.CustomerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;

@Service
public class UserDetailsService implements org.springframework.security.core.userdetails.UserDetailsService {

    @Autowired
    CustomerRepository customerRepository;

    @Override
    @Transactional
    public UserDetails loadUserByUsername(String userName) throws UsernameNotFoundException {
        Customer customer = customerRepository.findCustomerByEmail(userName)
                .orElseThrow(()->new ReadingIsGoodException(userName, ExceptionCode.USER_NOT_FOUND));
        return UserDetailsImpl.build(customer);
    }
}
