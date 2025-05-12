package com.prueba.tecnica.employeeservice.domain.interfaces.repositories;

import com.prueba.tecnica.employeeservice.domain.entities.Employee;

import java.util.List;
import java.util.Optional;

public interface EmployeeQueryRepository {
    Optional<Employee> findById(Long id);

    List<Employee> findAll();
}
