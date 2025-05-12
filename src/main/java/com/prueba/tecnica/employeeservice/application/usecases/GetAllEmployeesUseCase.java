package com.prueba.tecnica.employeeservice.application.usecases;

import com.prueba.tecnica.employeeservice.domain.entities.Employee;
import com.prueba.tecnica.employeeservice.domain.interfaces.repositories.EmployeeQueryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class GetAllEmployeesUseCase {

    private final EmployeeQueryRepository employeeQueryRepository;

    @Transactional
    public List<Employee> execute() {
        return employeeQueryRepository.findAll();
    }
}
