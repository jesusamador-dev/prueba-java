package com.prueba.tecnica.employeeservice.application.usecases;

import com.prueba.tecnica.employeeservice.domain.interfaces.repositories.EmployeeDeleteRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class DeleteEmployeeUseCase {

    private final EmployeeDeleteRepository employeeDeleteRepository;

    @Transactional
    public void execute(Long id) {
        employeeDeleteRepository.deleteById(id);
    }
}
