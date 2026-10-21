package com.goon.customerservice.service;

import com.goon.customerservice.entity.Customer;
import com.goon.customerservice.repository.CustomerRepository;
import com.goon.customerservice.security.JwtSigner;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final CustomerRepository repository;
    private final PasswordEncoder passwordEncoder;
    private final JwtSigner jwtSigner;


    public String login(LoginRequest request){
        Customer customer = repository.findByEmailOrPhone(
                request.identifier(),
                request.identifier())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.UNAUTHORIZED,"Invalid Credentials"));

        if(!passwordEncoder.matches(
                request.password(),customer.getPasswordHash()
        )){
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED,"Invalid credentials");
        }

        if(!customer.isEnabled()){
            throw new ResponseStatusException(
                    HttpStatus.FORBIDDEN, "Account disabled");
        }

        return jwtSigner.generateToken(customer.getCustomerId(),Role.CUSTOMER);
    }
}
