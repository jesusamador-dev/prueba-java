package com.prueba.tecnica.employeeservice.domain.entities;

import lombok.*;

import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Employee {

    private Long id;
    private String firstName;
    private String middleName;
    private String lastName;
    private String secondLastName;
    private Integer age;
    private String gender;
    private LocalDate birthDate;
    private String position;
}
