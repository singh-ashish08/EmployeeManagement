package com.emp.service;

import java.util.List;

import com.emp.entity.Employee;

public interface EmployeeService {
	public Employee getEmployee(int id);

	public List<Employee> getAll();

	public Employee createEmployee(Employee employee);

	public void deleteEmployee(int id);

	public Employee updateEmployee(int id, Employee emp);

}
