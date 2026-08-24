package com.me.demo1.mapper;

import com.me.demo1.dto.EmployeeRequest;
import com.me.demo1.dto.EmployeeResponse;
import com.me.demo1.model.Employee;
import org.springframework.stereotype.Component;

@Component   // ← ចាំបាច់ត្រូវការ ដើម្បីឲ្យ Spring inject class នេះទៅ Service បាន
public class EmployeeMapper {

    // DTO Request → Entity (សម្រាប់ពេល save ចូល database)
    public Employee toEntity(EmployeeRequest request) {
        Employee employee = new Employee();
        employee.setName(request.getName());
        employee.setDepartment(request.getDepartment());
        employee.setSalary(request.getSalary());
        return employee;
    }

    // Entity → DTO Response (សម្រាប់ពេល return ទៅ client)
    public EmployeeResponse toResponse(Employee employee) {
        return new EmployeeResponse(
                employee.getId(),
                employee.getName(),
                employee.getDepartment(),
                employee.getSalary(),
                employee.getImageUrl()
        );
    }
}