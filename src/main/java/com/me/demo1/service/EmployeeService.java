package com.me.demo1.service;

import com.me.demo1.dto.EmployeeRequest;
import com.me.demo1.dto.EmployeeResponse;
import com.me.demo1.exception.ResourceNotFoundException;
import com.me.demo1.mapper.EmployeeMapper;
import com.me.demo1.model.Employee;
import com.me.demo1.repository.EmployeeRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class EmployeeService {

    private final EmployeeRepository repository;
    private final EmployeeMapper mapper;   // 🆕 inject Mapper

    public EmployeeService(EmployeeRepository repository, EmployeeMapper mapper) {
        this.repository = repository;
        this.mapper = mapper;
    }

    public EmployeeResponse createEmployee(EmployeeRequest request) {
        Employee employee = mapper.toEntity(request);       // DTO → Entity
        Employee saved = repository.save(employee);
        return mapper.toResponse(saved);                    // Entity → DTO
    }

    public List<EmployeeResponse> getAllEmployees() {
        return repository.findAll().stream()
                .map(mapper::toResponse)                     // Entity list → DTO list
                .collect(Collectors.toList());
    }

    public EmployeeResponse getEmployeeById(Long id) {
        Employee employee = findEmployeeEntity(id);
        return mapper.toResponse(employee);
    }

    public EmployeeResponse updateEmployee(Long id, EmployeeRequest request) {
        Employee employee = findEmployeeEntity(id);
        employee.setName(request.getName());
        employee.setDepartment(request.getDepartment());
        employee.setSalary(request.getSalary());
        Employee updated = repository.save(employee);
        return mapper.toResponse(updated);
    }

    public void deleteEmployee(Long id) {
        Employee employee = findEmployeeEntity(id);
        repository.delete(employee);
    }

    // Helper method ខាងក្នុង (មិន expose ចេញក្រៅ Service)
    private Employee findEmployeeEntity(Long id) {
        return repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Employee not found with id: " + id));
    }
}