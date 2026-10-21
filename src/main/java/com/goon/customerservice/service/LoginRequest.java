package com.goon.customerservice.service;

public record LoginRequest(
        String identifier,  //email or phone
        String password
) {
}
