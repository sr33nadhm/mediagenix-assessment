package com.mediagenix.assessment.exception;


import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import com.mediagenix.assessment.exception.exceptions.BookExistsException;
import com.mediagenix.assessment.exception.exceptions.BookNotFoundException;
import com.mediagenix.assessment.exception.exceptions.CollectionNameExistsException;
import com.mediagenix.assessment.exception.exceptions.CollectionNotFoundException;

import jakarta.validation.ConstraintViolationException;

@ControllerAdvice
public class GlobalExceptionHandler {
	
	@ExceptionHandler(BookNotFoundException.class)
	public ResponseEntity<ApiResponse> bookNotFound(BookNotFoundException ex){
		ApiResponse apiResponse = ApiResponse.builder().status(HttpStatus.NOT_FOUND).message(ex.getMessage()).build();
        return new ResponseEntity<>(apiResponse, HttpStatus.BAD_REQUEST);
	}
	
	@ExceptionHandler(BookExistsException.class)
	public ResponseEntity<ApiResponse> bookExists(BookExistsException ex){
		ApiResponse apiResponse = ApiResponse.builder().status(HttpStatus.CONFLICT).message(ex.getMessage()).build();
        return new ResponseEntity<>(apiResponse, HttpStatus.BAD_REQUEST);
	}
	
	@ExceptionHandler(CollectionNotFoundException.class)
	public ResponseEntity<ApiResponse> collectionNotFound(CollectionNotFoundException ex){
		ApiResponse apiResponse = ApiResponse.builder().status(HttpStatus.NOT_FOUND).message(ex.getMessage()).build();
        return new ResponseEntity<>(apiResponse, HttpStatus.BAD_REQUEST);
	}
	
	@ExceptionHandler(CollectionNameExistsException.class)
	public ResponseEntity<ApiResponse> collectionExists(CollectionNameExistsException ex){
		ApiResponse apiResponse = ApiResponse.builder().status(HttpStatus.CONFLICT).message(ex.getMessage()).build();
        return new ResponseEntity<>(apiResponse, HttpStatus.BAD_REQUEST);
	}
	
	@ExceptionHandler({NullPointerException.class})
    public ResponseEntity<ApiResponse> nullPointerException(NullPointerException ex){
        ApiResponse apiResponse = ApiResponse.builder().status(HttpStatus.INTERNAL_SERVER_ERROR).message(ex.getMessage()).build();
        return new ResponseEntity<>(apiResponse, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler({IllegalArgumentException.class})
    public ResponseEntity<ApiResponse> illegalArgumentException(IllegalArgumentException ex){
        ApiResponse apiResponse = ApiResponse.builder().status(HttpStatus.INTERNAL_SERVER_ERROR).message(ex.getMessage()).build();
        return new ResponseEntity<>(apiResponse, HttpStatus.INTERNAL_SERVER_ERROR);
    }
    
    @ExceptionHandler({ConstraintViolationException.class})
    public ResponseEntity<ApiResponse> constraintException(ConstraintViolationException ex){
        ApiResponse apiResponse = ApiResponse.builder().status(HttpStatus.NOT_ACCEPTABLE).message("Book ISBN is invalid").build();
        return new ResponseEntity<>(apiResponse, HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
