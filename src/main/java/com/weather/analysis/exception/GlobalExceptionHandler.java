package com.weather.analysis.exception;

import java.util.HashMap;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

	@ExceptionHandler(MethodArgumentNotValidException.class)
	public ResponseEntity<Map<String, String>> handleValidationExceptions(MethodArgumentNotValidException ex) {
		Map<String, String> errors = new HashMap<>();
		ex.getBindingResult().getFieldErrors().forEach(error -> 
		errors.put(error.getField(), error.getDefaultMessage()));
		return new ResponseEntity<>(errors, HttpStatus.BAD_REQUEST);
	}


	@ExceptionHandler(Exception.class)
	public ResponseEntity<Map<String, Object>> handleGlobalException(Exception ex) {
		Map<String, Object> response = new HashMap<>();
		response.put("message",ex.getMessage());
		return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
	}

	@ExceptionHandler(HttpMessageNotReadableException.class)
	public ResponseEntity<String> handleEnumError(HttpMessageNotReadableException ex) {
		if (ex.getMessage().contains("StatisticType")) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Statistic value should be MIN or MAX or SUM or AVG");
		}
		if (ex.getMessage().contains("MetricsType")) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("MetricsType value should be TEMPERATURE or HUMIDITY or WINDSPEED");
		}
		return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Invalid request format");
	}
}
