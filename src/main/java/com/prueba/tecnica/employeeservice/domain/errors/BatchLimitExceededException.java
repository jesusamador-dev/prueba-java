package com.prueba.tecnica.employeeservice.domain.errors;

import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

public class BatchLimitExceededException extends ResponseStatusException {
    public BatchLimitExceededException(int limit) {
        super(HttpStatus.BAD_REQUEST, "Batch size exceeds the maximum allowed limit of " + limit + " employees.");
    }
}
