package com.emp.service;

import java.util.List;
import java.util.Map;

import com.emp.dto.EmployeeDto;

public interface EmployeeService {
	public EmployeeDto getEmployee(int id);

	public List<EmployeeDto> getAll();

	public EmployeeDto createEmployee(EmployeeDto employeeDto);

	public void deleteEmployee(int id);

	public EmployeeDto updateEmployee(int id, EmployeeDto employeeDto);

	public EmployeeDto partialUpdate(Map<String, Object> key, int id);

	public List<EmployeeDto> findByName(String name);

}
