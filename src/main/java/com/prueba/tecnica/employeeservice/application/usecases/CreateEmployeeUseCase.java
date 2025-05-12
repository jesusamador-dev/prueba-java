package com.prueba.tecnica.employeeservice.application.usecases;

import com.prueba.tecnica.employeeservice.domain.entities.Employee;
import com.prueba.tecnica.employeeservice.domain.interfaces.repositories.EmployeeSaveRepository;
import com.prueba.tecnica.employeeservice.domain.services.EmployeeValidationService;
import com.prueba.tecnica.employeeservice.presentation.api.rest.dto.CreateEmployeeRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class CreateEmployeeUseCase {

    private final EmployeeSaveRepository employeeSaveRepository;
    private final EmployeeValidationService employeeValidationService;

    @Transactional
    public Employee execute(CreateEmployeeRequest request) {

        employeeValidationService.validateMiniumAge(request.getAge());

        Employee employee = new Employee(
                null,
                request.getFirstName(),
                request.getMiddleName(),
                request.getLastName(),
                request.getSecondLastName(),
                request.getAge(),
                request.getGender(),
                request.getBirthDate(),
                request.getPosition()
        );
        return employeeSaveRepository.save(employee);
    }
}
