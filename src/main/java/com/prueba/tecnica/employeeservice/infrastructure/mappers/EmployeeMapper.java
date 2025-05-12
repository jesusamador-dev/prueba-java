package com.prueba.tecnica.employeeservice.infrastructure.mappers;

import com.prueba.tecnica.employeeservice.domain.entities.Employee;
import com.prueba.tecnica.employeeservice.infrastructure.entities.EmployeeEntity;

public class EmployeeMapper {

    public static EmployeeEntity toEntity(Employee employee) {
        if (employee == null) return null;
        return new EmployeeEntity(
                employee.getId(),
                employee.getFirstName(),
                employee.getMiddleName(),
                employee.getLastName(),
                employee.getSecondLastName(),
                employee.getAge(),
                employee.getGender(),
                employee.getBirthDate(),
                employee.getPosition()
        );
    }

    public static Employee toDomain(EmployeeEntity entity) {
        if (entity == null) return null;
        return new Employee(
                entity.getId(),
                entity.getFirstName(),
                entity.getMiddleName(),
                entity.getLastName(),
                entity.getSecondLastName(),
                entity.getAge(),
                entity.getGender(),
                entity.getBirthDate(),
                entity.getPosition()
        );
    }
}
