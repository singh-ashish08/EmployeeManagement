package com.emp.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.emp.entity.Employee;
import com.emp.repository.EmployeeRepository;

@Service
public class EmployeeServiceImpl implements EmployeeService {

	@Autowired
	private EmployeeRepository employeeRepository;

	@Override
	public Employee getEmployee(int id) {
		// TODO Auto-generated method stub
		return employeeRepository.findById(id).get();
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
		employeeRepository.deleteById(id);
	}

	@Override
	public Employee updateEmployee(int id, Employee emp) {
		// TODO Auto-generated method stub
		Employee oldEmployee = employeeRepository.findById(id).get();
		oldEmployee.setName(emp.getName());
		oldEmployee.setDob(emp.getDob());
		oldEmployee.setMob(emp.getMob());
		oldEmployee.setEmpId(emp.getEmpId());
		oldEmployee.setId(emp.getId());
		return employeeRepository.save(oldEmployee);
	}

}
