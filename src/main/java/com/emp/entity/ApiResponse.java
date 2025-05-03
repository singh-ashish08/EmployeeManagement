package com.emp.entity;

import java.time.LocalDateTime;

import lombok.Data;

@Data
public class ApiResponse {
	private String message;
	private int status;
	private LocalDateTime timestamp;
	private String error;
}
