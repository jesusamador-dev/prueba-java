package com.prueba.tecnica.employeeservice.domain.services;

import com.prueba.tecnica.employeeservice.domain.errors.BatchLimitExceededException;
import com.prueba.tecnica.employeeservice.domain.errors.InvalidEmployeeAgeException;
import org.springframework.stereotype.Component;

@Component
public class EmployeeValidationService {

    private static final int MAX_BATCH_SIZE = 10;
    private static final int MIN_AGE = 18;

    public void validateBatchSize(int count) {
        if (count > MAX_BATCH_SIZE) {
            throw new BatchLimitExceededException(MAX_BATCH_SIZE);
        }
    }

    public void validateMiniumAge(int age) {
        if (age < MIN_AGE) {
            throw new InvalidEmployeeAgeException(age);
        }
    }
}
