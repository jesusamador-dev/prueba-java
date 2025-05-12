package com.prueba.tecnica.employeeservice.presentation.api.rest.controllers;

import com.prueba.tecnica.employeeservice.domain.entities.Employee;
import com.prueba.tecnica.employeeservice.presentation.api.rest.dto.*;
import com.prueba.tecnica.employeeservice.application.usecases.*;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
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

    @Operation(summary = "Listar todos los empleados")
    @ApiResponse(responseCode = "200", description = "Lista obtenida exitosamente")
    @GetMapping
    public ResponseEntity<List<EmployeeResponse>> getAll() {
        List<Employee> employees = getAllEmployeesUseCase.execute();
        List<EmployeeResponse> response = employees.stream()
                .map(this::toResponse)
                .collect(Collectors.toList());
        return ResponseEntity.ok(response);
    }

    @Operation(summary = "Crear un nuevo empleado")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Empleado creado exitosamente"),
            @ApiResponse(responseCode = "400", description = "Error de validación en la petición")
    })
    @PostMapping
    public ResponseEntity<EmployeeResponse> create(@RequestBody @Valid CreateEmployeeRequest request) {
        Employee created = createEmployeeUseCase.execute(request);
        return ResponseEntity.ok(toResponse(created));
    }

    @Operation(summary = "Crear empleados en lote (máximo 10)")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "207", description = "Procesamiento parcial: algunos empleados fueron rechazados"),
            @ApiResponse(responseCode = "400", description = "Error de validación general")
    })
    @PostMapping("/batch")
    public ResponseEntity<BatchCreateEmployeeResponse> createBatch(
            @RequestBody @Valid BatchCreateEmployeeRequest requestWrapper) {

        List<CreateEmployeeRequest> requestList = requestWrapper.getEmployees();
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

    @Operation(summary = "Actualizar un empleado por ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Empleado actualizado exitosamente"),
            @ApiResponse(responseCode = "400", description = "Datos de entrada inválidos"),
            @ApiResponse(responseCode = "404", description = "Empleado no encontrado")
    })
    @PutMapping("/{id}")
    public ResponseEntity<EmployeeResponse> update(@PathVariable Long id,
                                                   @RequestBody @Valid UpdateEmployeeRequest request) {
        Employee updated = updateEmployeeUseCase.execute(id, request);
        return ResponseEntity.ok(toResponse(updated));
    }

    @Operation(summary = "Eliminar un empleado por ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Empleado eliminado exitosamente"),
            @ApiResponse(responseCode = "404", description = "Empleado no encontrado")
    })
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
