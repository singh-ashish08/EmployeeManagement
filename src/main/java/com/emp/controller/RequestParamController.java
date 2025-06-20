package com.emp.controller;

import com.emp.dto.EmployeeDto;
import com.emp.entity.Employee;
import com.emp.service.EmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/attributes")
public class RequestParamController {
@Autowired
private EmployeeService employeeService;

    @GetMapping("/get")
    public ResponseEntity<List<EmployeeDto>> getEmployee(@RequestParam(name="employee")String name){
        List<EmployeeDto> byName = employeeService.findByName(name);
        return new ResponseEntity<>(byName,HttpStatus.OK);
    }

    @GetMapping("/value")
    public ResponseEntity<List<EmployeeDto>> getByValue(@RequestParam(value="yes")String name){
        List<EmployeeDto> byName = employeeService.findByName(name);
        return new ResponseEntity<>(byName,HttpStatus.OK);
    }

    @GetMapping("/default")
    public ResponseEntity<List<EmployeeDto>> getEmployeeByDefault(@RequestParam(value="employee",required = false,defaultValue = "DEFAULT_EMPTY")String name){

        List<EmployeeDto> byName = employeeService.findByName(name);
        return new ResponseEntity<>(byName,HttpStatus.OK);
    }

}
