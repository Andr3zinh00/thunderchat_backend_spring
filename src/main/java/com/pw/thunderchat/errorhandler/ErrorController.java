package com.pw.thunderchat.errorhandler;

import java.time.LocalDateTime;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class ErrorController{


	@ExceptionHandler(NotFoundException.class)
	public ResponseEntity<ErrorResponse> handleNotFound(NotFoundException ex) {
		return new ResponseEntity<ErrorResponse>(new ErrorResponse(ex.getMessage(), "NOT_FOUND", LocalDateTime.now()),
				HttpStatus.NOT_FOUND);
	}

	@ExceptionHandler(BadRequestException.class)
	public ResponseEntity<ErrorResponse> handleBadRequest(BadRequestException ex) {
		return new ResponseEntity<ErrorResponse>(new ErrorResponse(ex.getMessage(), "BAD_REQUEST", LocalDateTime.now()),
				HttpStatus.BAD_REQUEST);
	}
	
	@ExceptionHandler(InvalidOperationException.class)
	public ResponseEntity<ErrorResponse> handleInvalidOperation(InvalidOperationException ex) {
		return new ResponseEntity<ErrorResponse>(new ErrorResponse(ex.getMessage(), "CONFLICT", LocalDateTime.now()),
				HttpStatus.CONFLICT);
	}
	
}
