package com.emp.service;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.emp.dto.EmployeeDto;
import com.emp.entity.Employee;
import com.emp.exception.EmployeeNotFoundException;
import com.emp.repository.EmployeeRepository;

@Service
@Slf4j
public class EmployeeServiceImpl implements EmployeeService {
private static final Logger logger= LoggerFactory.getLogger(EmployeeServiceImpl.class);
	@Autowired
	private EmployeeRepository employeeRepository;
	@Autowired
	private ModelMapper modelMapper;

	String notFound = "Employee not found with the given id : ";

	@Override
	public EmployeeDto getEmployee(int id) {
		Optional<Employee> findById = employeeRepository.findById(id);
		Employee orElseThrow = findById.orElseThrow(() -> new EmployeeNotFoundException(notFound + id));
		EmployeeDto employeeDto = modelMapper.map(orElseThrow, EmployeeDto.class);
		return employeeDto;
	}

	@Override
	public List<EmployeeDto> getAll() {
		List<Employee> allEmployees = employeeRepository.findAll();
		if (allEmployees.isEmpty()) {
			throw new EmployeeNotFoundException("No employees found");
		}
		return allEmployees.stream().map(employee -> modelMapper.map(employee, EmployeeDto.class)).toList();

	}

	@Override
	public EmployeeDto createEmployee(EmployeeDto employee) {
		Optional<Employee> existingEmployee = employeeRepository.findByMob(employee.getMob());
		if (existingEmployee.isPresent()) {
			throw new EmployeeNotFoundException(
					"Employee already exists with the given mobile number: " + employee.getMob());
		} else {
			Employee employeeEntity = modelMapper.map(employee, Employee.class);
			Employee save = employeeRepository.save(employeeEntity);

			return modelMapper.map(save, EmployeeDto.class);
		}
	}

	@Override
	public void deleteEmployee(int id) {
		Employee employee = employeeRepository.findById(id)
				.orElseThrow(() -> new EmployeeNotFoundException(notFound + id));
		employeeRepository.delete(employee);
	}

	@Override
	public EmployeeDto updateEmployee(int id, EmployeeDto emp) {
		Employee employee = employeeRepository.findById(id)
				.orElseThrow(() -> new EmployeeNotFoundException(notFound + id));
		employee.setName(emp.getName());
		employee.setEmpId(emp.getEmpId());
		employee.setMob(emp.getMob());
		employee.setDob(emp.getDob());
		Employee save = employeeRepository.save(employee);
		return modelMapper.map(save, EmployeeDto.class);

	}

	@Override
	public EmployeeDto partialUpdate(Map<String, Object> updates, int id) {
		Employee employee = employeeRepository.findById(id)
				.orElseThrow(() -> new EmployeeNotFoundException(notFound + id));
		updates.forEach((key, value) -> {
			switch (key) {
			case "name":
				employee.setName((String) value);
				break;
			case "empId":
				employee.setEmpId((String) value);
				break;
			case "mob":
				employee.setMob((String) value);
				break;
			case "dob":
				employee.setDob(LocalDate.parse((String) value));
				break;
			default:
				throw new IllegalArgumentException("Invalid field: " + key);
			}
		});
		Employee save = employeeRepository.save(employee);
		return modelMapper.map(save, EmployeeDto.class);

	}

	@Override
	public List<EmployeeDto> findByName(String name) {
		Optional<List<Employee>> findByName = employeeRepository.findByName(name);
        findByName.ifPresent(employees -> logger.info("Data from DB : {}", employees));
		if (findByName.isEmpty()) {
			throw new EmployeeNotFoundException("No employees found with the name: " + name);
		}else{
			List<Employee> employees = findByName.get();
			List<EmployeeDto> list = employees.stream().map(employee -> modelMapper.map(employee, EmployeeDto.class)).toList();
			logger.info("Employee ServiceIMPL : {} ",list);
			return list;

		}}

}
