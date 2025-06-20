package com.emp.exception;

import java.time.LocalDateTime;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.emp.entity.ApiResponse;

@RestControllerAdvice
public class GlobalExceptionHandler {
	@ExceptionHandler(EmployeeNotFoundException.class)
	public ResponseEntity<ApiResponse> handleEmployeeNotFoundException(EmployeeNotFoundException ex) {
		ApiResponse response = new ApiResponse();
		response.setTimestamp(LocalDateTime.now());
		response.setStatus(HttpStatus.NOT_FOUND.value());
		response.setError("Not Found");
		response.setMessage(ex.getMessage());

		return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
	}

	@ExceptionHandler(AlreadyPresentException.class)
	public ResponseEntity<ApiResponse> handleAlreadyPresentException(AlreadyPresentException ex) {
		ApiResponse response = new ApiResponse();
		response.setTimestamp(LocalDateTime.now());
		response.setStatus(HttpStatus.CONFLICT.value());
		response.setError("Conflict");
		response.setMessage(ex.getMessage());

		return new ResponseEntity<>(response, HttpStatus.CONFLICT);
	}

}
