package com.prueba.tecnica.employeeservice.presentation.api.rest.dto;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotEmpty;
import lombok.Data;

import java.util.List;

@Data
public class BatchCreateEmployeeRequest {

    @Valid
    @NotEmpty(message = "La lista de empleados no puede estar vacía")
    private List<CreateEmployeeRequest> employees;
}
