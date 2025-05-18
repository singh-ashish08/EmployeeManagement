package com.emp.dto;

import java.time.LocalDate;

import lombok.Data;

@Data
public class EmployeeDto {
	private int id;
	private String name;
	private String empId;
	private String mob;
	private LocalDate dob;
}
