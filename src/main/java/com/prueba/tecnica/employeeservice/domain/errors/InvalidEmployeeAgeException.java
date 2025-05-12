package com.prueba.tecnica.employeeservice.domain.errors;

import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

public class InvalidEmployeeAgeException extends ResponseStatusException {
    public InvalidEmployeeAgeException(int age) {
        super(HttpStatus.BAD_REQUEST, "Employee must be at least 18 years old. Provided: " + age);
    }
}
