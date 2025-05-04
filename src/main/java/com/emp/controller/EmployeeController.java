package com.emp.controller;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.emp.entity.Employee;
import com.emp.service.EmployeeService;

@RestController
@RequestMapping("/employee")
public class EmployeeController {
	@Autowired
	private EmployeeService employeeService;

	@GetMapping("/{id}")
	public ResponseEntity<Employee> getEmployee(@PathVariable("id") int id) {
		Employee employee = employeeService.getEmployee(id);
		return new ResponseEntity<>(employee, HttpStatus.OK);
	}

	@GetMapping
	public ResponseEntity<List<Employee>> getEmployees() {
		List<Employee> allEmployees = employeeService.getAll();
		return new ResponseEntity<>(allEmployees, HttpStatus.OK);
	}

	@PostMapping("/create")
	public ResponseEntity<Employee> createEmployee(@RequestBody Employee employee) {
		Employee createEmployee = employeeService.createEmployee(employee);
		return new ResponseEntity<>(createEmployee, HttpStatus.CREATED);
	}

	@PutMapping("/update/{id}")
	public ResponseEntity<Employee> updateEmployee(@RequestBody Employee employee, @PathVariable("id") int id) {
		Employee updateEmployee = employeeService.updateEmployee(id, employee);
		return new ResponseEntity<>(updateEmployee, HttpStatus.OK);

	}

	@DeleteMapping("/delete/{id}")
	public ResponseEntity<String> deleteEmployee(@PathVariable("id") int id) {
		employeeService.deleteEmployee(id);
		return new ResponseEntity<>("Employee Deleted Successfully :", HttpStatus.NO_CONTENT);
	}

	@PatchMapping("/pupdate/{id}")
	public ResponseEntity<Employee> partialUpdate(@RequestBody Map<String, Object> key, @PathVariable("id") int id) {
		Employee partialUpdate = employeeService.partialUpdate(key, id);
		return new ResponseEntity<>(partialUpdate, HttpStatus.OK);
	}

	@GetMapping("/find ")
	public ResponseEntity<List<Employee>> findByName(@RequestParam("name") String name) {
		List<Employee> findByName = employeeService.findByName(name);
		return new ResponseEntity<>(findByName, HttpStatus.OK);
	}
}
