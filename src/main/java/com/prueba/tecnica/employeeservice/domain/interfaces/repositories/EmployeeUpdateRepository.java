package com.prueba.tecnica.employeeservice.domain.interfaces.repositories;

import com.prueba.tecnica.employeeservice.domain.entities.Employee;

public interface EmployeeUpdateRepository {
    Employee update(Long id, Employee employee);
}