package com.emp.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.emp.entity.Employee;

public interface EmployeeRepository extends JpaRepository<Employee, Integer> {
	public Optional<List<Employee>> findByName(String name);
	
	
}
