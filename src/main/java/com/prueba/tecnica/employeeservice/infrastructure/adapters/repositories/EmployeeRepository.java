package com.prueba.tecnica.employeeservice.infrastructure.adapters.repositories;

import com.prueba.tecnica.employeeservice.domain.entities.Employee;
import com.prueba.tecnica.employeeservice.domain.interfaces.repositories.EmployeeDeleteRepository;
import com.prueba.tecnica.employeeservice.domain.interfaces.repositories.EmployeeQueryRepository;
import com.prueba.tecnica.employeeservice.domain.interfaces.repositories.EmployeeSaveRepository;
import com.prueba.tecnica.employeeservice.domain.interfaces.repositories.EmployeeUpdateRepository;
import com.prueba.tecnica.employeeservice.infrastructure.entities.EmployeeEntity;
import com.prueba.tecnica.employeeservice.infrastructure.mappers.EmployeeMapper;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.springframework.data.jpa.repository.support.SimpleJpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Repository
public class EmployeeRepository implements
        EmployeeSaveRepository,
        EmployeeUpdateRepository,
        EmployeeDeleteRepository,
        EmployeeQueryRepository {

    @PersistenceContext
    private EntityManager entityManager;
    private final SimpleJpaRepository<EmployeeEntity, Long> jpaRepository;

    public EmployeeRepository(EntityManager entityManager) {
        this.entityManager = entityManager;
        this.jpaRepository = new SimpleJpaRepository<>(EmployeeEntity.class, this.entityManager);
    }

    @Override
    public Employee save(Employee employee) {
        EmployeeEntity entity = jpaRepository.save(EmployeeMapper.toEntity(employee));
        return EmployeeMapper.toDomain(entity);
    }

    @Override
    public Employee update(Long id, Employee employee) {
        if (!jpaRepository.existsById(id)) {
            throw new IllegalArgumentException("Employee not found with id " + id);
        }
        employee.setId(id);
        EmployeeEntity entity = jpaRepository.save(EmployeeMapper.toEntity(employee));
        return EmployeeMapper.toDomain(entity);
    }

    @Override
    public void deleteById(Long id) {
        jpaRepository.deleteById(id);
    }

    @Override
    public Optional<Employee> findById(Long id) {
        return jpaRepository.findById(id).map(EmployeeMapper::toDomain);
    }

    @Override
    public List<Employee> findAll() {
        return jpaRepository.findAll()
                .stream()
                .map(EmployeeMapper::toDomain)
                .collect(Collectors.toList());
    }
}
