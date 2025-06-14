package com.emp.controller;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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
import org.springframework.web.multipart.MultipartFile;

import com.emp.dto.EmployeeDto;
import com.emp.service.EmployeeService;
import com.fasterxml.jackson.databind.ObjectMapper;
@Slf4j
@RestController
@RequestMapping("/employee")
public class EmployeeController {
	private static final Logger logger= LoggerFactory.getLogger(EmployeeController.class);
	@Autowired
	private EmployeeService employeeService;
	@Autowired
	private ObjectMapper objectMapper;

	@GetMapping("/{id}")
	public ResponseEntity<EmployeeDto> getEmployee(@PathVariable int id) {
		EmployeeDto employee = employeeService.getEmployee(id);
		return new ResponseEntity<>(employee, HttpStatus.OK);
	}

	@GetMapping
	public ResponseEntity<List<EmployeeDto>> getEmployees() {
		List<EmployeeDto> allEmployees = employeeService.getAll();
		return new ResponseEntity<>(allEmployees, HttpStatus.OK);
	}

	@PostMapping("/create")
	public ResponseEntity<EmployeeDto> createEmployee(@RequestBody EmployeeDto employee) {
		EmployeeDto createEmployee = employeeService.createEmployee(employee);
		return new ResponseEntity<>(createEmployee, HttpStatus.CREATED);
	}

	@PutMapping("/update/{id}")
	public ResponseEntity<EmployeeDto> updateEmployee(@RequestBody EmployeeDto employee, @PathVariable int id) {
		EmployeeDto updateEmployee = employeeService.updateEmployee(id, employee);
		return new ResponseEntity<>(updateEmployee, HttpStatus.OK);

	}

	@DeleteMapping("/delete/{id}")
	public ResponseEntity<String> deleteEmployee(@PathVariable int id) {
		employeeService.deleteEmployee(id);
		return new ResponseEntity<>("Employee Deleted Successfully :", HttpStatus.NO_CONTENT);
	}

	@PatchMapping("/pupdate/{id}")
	public ResponseEntity<EmployeeDto> partialUpdate(@RequestBody Map<String, Object> key, @PathVariable int id) {
		EmployeeDto partialUpdate = employeeService.partialUpdate(key, id);
		return new ResponseEntity<>(partialUpdate, HttpStatus.OK);
	}

	@GetMapping("/find")
	public ResponseEntity<List<EmployeeDto>> findByName(@RequestParam String name) {
		List<EmployeeDto> findByName = employeeService.findByName(name);
        logger.info("Get data from database =  {}", findByName);
		return new ResponseEntity<>(findByName, HttpStatus.OK);
	}

	@PostMapping("/create_e")
	public ResponseEntity<EmployeeDto> createEmployee(@RequestParam MultipartFile file) {
		// file
		try {
			// Read file content and convert to String
			String json = new String(file.getBytes());

			// Convert JSON string to Employee object
			// ObjectMapper objectMapper = new ObjectMapper();
			EmployeeDto employee = objectMapper.readValue(json, EmployeeDto.class);

			// Save to DB
			EmployeeDto saved = employeeService.createEmployee(employee);
			return new ResponseEntity<>(saved, HttpStatus.CREATED);

		} catch (IOException e) {
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}
	}

}
