package com.me.demo1.controller;

import com.me.demo1.model.Employee;
import com.me.demo1.repository.EmployeeRepository;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;

import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.UUID;

@RestController
@RequestMapping("/employees")
public class EmployeeController {
    private final EmployeeRepository repository;
    private final String uploadDir = "uploads/";  // folder រក្សា image

    public EmployeeController(EmployeeRepository repository) {
        this.repository = repository;
    }

    // Endpoint upload image សម្រាប់ Employee ជាក់លាក់
    @PostMapping("/{id}/upload-image")
    public Employee uploadImage(@PathVariable Long id,
                                @RequestParam("file") MultipartFile file) throws IOException {
        Employee employee = repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Employee not found"));

        // បង្កើត folder uploads/ បើមិនទាន់មាន
        Path uploadPath = Paths.get(uploadDir);
        if (!Files.exists(uploadPath)) {
            Files.createDirectories(uploadPath);
        }

        // ដាក់ឈ្មោះ file ថ្មី (ជៀសវាង file ស្ទួនឈ្មោះ)
        String fileName = UUID.randomUUID() + "_" + file.getOriginalFilename();
        Path filePath = uploadPath.resolve(fileName);
        Files.copy(file.getInputStream(), filePath);

        // រក្សា path ចូល database
        employee.setImageUrl("/employees/image/" + fileName);
        return repository.save(employee);
    }

    // Endpoint ទាញយក/មើល image
    @GetMapping("/image/{fileName}")
    public Resource getImage(@PathVariable String fileName) throws MalformedURLException {
        Path filePath = Paths.get(uploadDir).resolve(fileName);
        return new UrlResource(filePath.toUri());
    }

    @PostMapping
    public Employee createEmployee(@RequestBody Employee employee) {
        return repository.save(employee);
    }

    @GetMapping
    public java.util.List<Employee> getAllEmployees() {
        return repository.findAll();
    }
}
