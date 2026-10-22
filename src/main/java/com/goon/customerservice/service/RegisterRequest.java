package com.goon.customerservice.service;

import jakarta.validation.constraints.Pattern;

public record RegisterRequest(
        String name,
        String email,

        @Pattern(regexp = "^[0-9]{10}$",message = "phone must be 10 digits")
        String phone,

        String password

) {
}
