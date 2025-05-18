package com.emp.service;

import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.emp.entity.Employee;
import com.emp.repository.EmployeeRepository;

@SpringBootTest
class EmployeeServiceTest {

	@Autowired
	private EmployeeRepository employeeRepository;

	@Test
	void testFindByUserName() {
		assertTrue(5 > 3);
		Optional<List<Employee>> findByName = employeeRepository.findByName("Ram");
		assertTrue(findByName.isEmpty());
	}
}
