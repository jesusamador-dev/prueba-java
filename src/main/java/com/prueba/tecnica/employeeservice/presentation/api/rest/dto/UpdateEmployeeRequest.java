package com.prueba.tecnica.employeeservice.presentation.api.rest.dto;

import jakarta.validation.constraints.*;
import lombok.Data;

import java.time.LocalDate;

@Data
public class UpdateEmployeeRequest {

    @NotBlank
    private String firstName;

    private String middleName;

    @NotBlank
    private String lastName;

    private String secondLastName;

    @NotNull
    @Min(18)
    private Integer age;

    @NotBlank
    private String gender;

    @NotNull
    private LocalDate birthDate;

    @NotBlank
    private String position;
}
