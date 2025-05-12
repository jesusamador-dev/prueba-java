package com.prueba.tecnica.employeeservice.presentation.api.rest.controllers;

import com.prueba.tecnica.employeeservice.domain.entities.Employee;
import com.prueba.tecnica.employeeservice.presentation.api.rest.dto.*;
import com.prueba.tecnica.employeeservice.application.usecases.*;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/employees")
@RequiredArgsConstructor
public class EmployeesController {

    private final CreateEmployeeUseCase createEmployeeUseCase;
    private final CreateEmployeeBatchUseCase createEmployeeBatchUseCase;
    private final GetAllEmployeesUseCase getAllEmployeesUseCase;
    private final UpdateEmployeeUseCase updateEmployeeUseCase;
    private final DeleteEmployeeUseCase deleteEmployeeUseCase;

    @GetMapping
    public ResponseEntity<List<EmployeeResponse>> getAll() {
        List<Employee> employees = getAllEmployeesUseCase.execute();
        List<EmployeeResponse> response = employees.stream()
                .map(this::toResponse)
                .collect(Collectors.toList());
        return ResponseEntity.ok(response);
    }

    @PostMapping
    public ResponseEntity<EmployeeResponse> create(@RequestBody @Valid CreateEmployeeRequest request) {
        Employee created = createEmployeeUseCase.execute(request);
        return ResponseEntity.ok(toResponse(created));
    }

    @PostMapping("/batch")
    public ResponseEntity<BatchCreateEmployeeResponse> createBatch(@RequestBody @Valid List<CreateEmployeeRequest> requestList) {
        Map<String, Object> result = createEmployeeBatchUseCase.execute(requestList);

        List<Employee> created = (List<Employee>) result.get("created");
        List<Map<String, Object>> rejected = (List<Map<String, Object>>) result.get("rejected");

        List<EmployeeResponse> createdResponses = created.stream()
                .map(this::toResponse)
                .toList();

        List<InvalidEmployeeResponse> rejectedResponses = rejected.stream()
                .map(err -> InvalidEmployeeResponse.builder()
                        .reason((String) err.get("reason"))
                        .firstName((String) err.get("firstName"))
                        .lastName((String) err.get("lastName"))
                        .age((Integer) err.get("age"))
                        .build())
                .toList();

        BatchCreateEmployeeResponse response = BatchCreateEmployeeResponse.builder()
                .created(createdResponses)
                .rejected(rejectedResponses)
                .build();

        return ResponseEntity.status(207).body(response);
    }

    @PutMapping("/{id}")
    public ResponseEntity<EmployeeResponse> update(@PathVariable Long id,
                                                   @RequestBody @Valid UpdateEmployeeRequest request) {
        Employee updated = updateEmployeeUseCase.execute(id, request);
        return ResponseEntity.ok(toResponse(updated));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        deleteEmployeeUseCase.execute(id);
        return ResponseEntity.noContent().build();
    }

    private EmployeeResponse toResponse(Employee e) {
        return EmployeeResponse.builder()
                .id(e.getId())
                .firstName(e.getFirstName())
                .middleName(e.getMiddleName())
                .lastName(e.getLastName())
                .secondLastName(e.getSecondLastName())
                .age(e.getAge())
                .gender(e.getGender())
                .birthDate(e.getBirthDate())
                .position(e.getPosition())
                .build();
    }
}
