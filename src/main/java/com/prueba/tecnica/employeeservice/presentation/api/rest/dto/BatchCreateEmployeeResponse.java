package com.prueba.tecnica.employeeservice.presentation.api.rest.dto;

import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class BatchCreateEmployeeResponse {
    private List<EmployeeResponse> created;
    private List<InvalidEmployeeResponse> rejected;
}
