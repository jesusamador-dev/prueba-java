package com.prueba.tecnica.employeeservice.application.usecases;

import com.prueba.tecnica.employeeservice.domain.entities.Employee;
import com.prueba.tecnica.employeeservice.domain.interfaces.repositories.EmployeeSaveRepository;
import com.prueba.tecnica.employeeservice.domain.services.EmployeeValidationService;
import com.prueba.tecnica.employeeservice.presentation.api.rest.dto.CreateEmployeeRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class CreateEmployeeBatchUseCase {

    private final EmployeeSaveRepository employeeSaveRepository;
    private final EmployeeValidationService employeeValidationService;

    @Transactional
    public Map<String, Object> execute(List<CreateEmployeeRequest> requestList) {
        employeeValidationService.validateBatchSize(requestList.size());

        List<Employee> created = new ArrayList<>();
        List<Map<String, Object>> rejected = new ArrayList<>();

        for (CreateEmployeeRequest req : requestList) {
            try {
                employeeValidationService.validateMiniumAge(req.getAge());

                Employee employee = new Employee(
                        null,
                        req.getFirstName(),
                        req.getMiddleName(),
                        req.getLastName(),
                        req.getSecondLastName(),
                        req.getAge(),
                        req.getGender(),
                        req.getBirthDate(),
                        req.getPosition()
                );

                created.add(employeeSaveRepository.save(employee));

            } catch (Exception ex) {
                Map<String, Object> error = new HashMap<>();
                error.put("reason", ex.getMessage());
                error.put("firstName", req.getFirstName());
                error.put("lastName", req.getLastName());
                error.put("age", req.getAge());
                rejected.add(error);
            }
        }

        Map<String, Object> result = new HashMap<>();
        result.put("created", created);
        result.put("rejected", rejected);
        return result;
    }
}
