package com.prueba.tecnica.employeeservice.presentation.api.rest.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class InvalidEmployeeResponse {
    private String reason;
    private String firstName;
    private String lastName;
    private Integer age;
}
