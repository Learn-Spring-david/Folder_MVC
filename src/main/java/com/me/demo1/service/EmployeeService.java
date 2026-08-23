package com.me.demo1.service;

import com.me.demo1.model.Employee;
import com.me.demo1.repository.EmployeeRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service   // ← Annotation ប្រាប់ Spring ថា class នេះជា Service Bean
public class EmployeeService {

    private final EmployeeRepository repository;

    // Constructor injection (Spring inject Repository ដោយស្វ័យប្រវត្តិ)
    public EmployeeService(EmployeeRepository repository) {
        this.repository = repository;
    }

    public Employee createEmployee(Employee employee) {
        // ទីនេះជាកន្លែងដាក់ business logic
        // ឧទាហរណ៍: validate បន្ថែម, គណនាអ្វីមួយ, ហៅ service ដទៃ...
        return repository.save(employee);
    }

    public List<Employee> getAllEmployees() {
        return repository.findAll();
    }

    public Employee getEmployeeById(Long id) {
        return repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Employee not found with id: " + id));
    }

    public Employee updateEmployee(Long id, Employee updatedEmployee) {
        Employee employee = getEmployeeById(id);
        employee.setName(updatedEmployee.getName());
        employee.setDepartment(updatedEmployee.getDepartment());
        employee.setSalary(updatedEmployee.getSalary());
        return repository.save(employee);
    }

    public void deleteEmployee(Long id) {
        Employee employee = getEmployeeById(id);
        repository.delete(employee);
    }
}