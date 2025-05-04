package com.emp.service;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.emp.entity.Employee;
import com.emp.exception.EmployeeNotFoundException;
import com.emp.repository.EmployeeRepository;

@Service
public class EmployeeServiceImpl implements EmployeeService {

	@Autowired
	private EmployeeRepository employeeRepository;

	@Override
	public Employee getEmployee(int id) {
		// TODO Auto-generated method stub
		Optional<Employee> findById = employeeRepository.findById(id);
		Employee orElseThrow = findById
				.orElseThrow(() -> new EmployeeNotFoundException("Employee not found with the given id : " + id));
		return orElseThrow;
	}

	@Override
	public List<Employee> getAll() {
		// TODO Auto-generated method stub
		return employeeRepository.findAll();
	}

	@Override
	public Employee createEmployee(Employee employee) {
		// TODO Auto-generated method stub
		return employeeRepository.save(employee);
	}

	@Override
	public void deleteEmployee(int id) {
		// TODO Auto-generated method stub
		Employee orElseThrow = employeeRepository.findById(id)
				.orElseThrow(() -> new EmployeeNotFoundException("Employee not found with the given id : " + id));
		employeeRepository.deleteById(id);
	}

	@Override
	public Employee updateEmployee(int id, Employee emp) {
		// TODO Auto-generated method stub
		Employee oldEmployee = employeeRepository.findById(id)
				.orElseThrow(() -> new EmployeeNotFoundException("Employee not found with the given id : " + id));

		oldEmployee.setName(emp.getName());
		oldEmployee.setDob(emp.getDob());
		oldEmployee.setMob(emp.getMob());
		oldEmployee.setEmpId(emp.getEmpId());
		oldEmployee.setId(emp.getId());
		return employeeRepository.save(oldEmployee);
	}

	@Override
	public Employee partialUpdate(Map<String, Object> updates, int id) {
		Employee employee = employeeRepository.findById(id)
				.orElseThrow(() -> new EmployeeNotFoundException("Employee not found with the given id : " + id));

		updates.forEach((key, value) -> {
			switch (key) {
			case "name" -> employee.setName((String) value);
			case "empId" -> employee.setEmpId((String) value);
			case "mob" -> employee.setMob((String) value);
			case "dob" -> {
				if (value instanceof String dobStr) {
					employee.setDob(LocalDate.parse(dobStr));
				} else {
					throw new IllegalArgumentException("Invalid format for dob. Expected ISO string (e.g. 2000-01-01)");
				}
			}
			default -> throw new IllegalArgumentException("Field not supported for update: " + key);
			}
		});

		return employeeRepository.save(employee);
	}

}
