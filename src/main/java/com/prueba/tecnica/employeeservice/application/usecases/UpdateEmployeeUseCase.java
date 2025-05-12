package com.prueba.tecnica.employeeservice.application.usecases;

import com.prueba.tecnica.employeeservice.domain.entities.Employee;
import com.prueba.tecnica.employeeservice.domain.interfaces.repositories.EmployeeUpdateRepository;
import com.prueba.tecnica.employeeservice.presentation.api.rest.dto.UpdateEmployeeRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class UpdateEmployeeUseCase {

    private final EmployeeUpdateRepository employeeUpdateRepository;

    @Transactional
    public Employee execute(Long id, UpdateEmployeeRequest request) {
        Employee employee = new Employee(
                id,
                request.getFirstName(),
                request.getMiddleName(),
                request.getLastName(),
                request.getSecondLastName(),
                request.getAge(),
                request.getGender(),
                request.getBirthDate(),
                request.getPosition()
        );

        return employeeUpdateRepository.update(id, employee);
    }
}
