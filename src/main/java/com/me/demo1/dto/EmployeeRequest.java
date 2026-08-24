package com.me.demo1.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class EmployeeRequest {
    @NotNull
    @Size(min = 3, max = 50)
    private String name;

    @NotNull
    @Size(min = 2, max = 30)
    private String department;

    @Min(0)
    private Double salary;
}