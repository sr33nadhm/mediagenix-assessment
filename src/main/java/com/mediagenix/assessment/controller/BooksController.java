package com.mediagenix.assessment.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.mediagenix.assessment.dto.BooksRequest;
import com.mediagenix.assessment.dto.BooksResponse;
import com.mediagenix.assessment.service.BookService;

@RestController
@RequestMapping("/books")
public class BooksController {
	
	@Autowired
	private BookService service;
	
	@PostMapping
    public ResponseEntity<BooksResponse> addBook(@RequestBody BooksRequest request) {
        return new ResponseEntity<>(service.addBook(request), HttpStatus.CREATED);
    }
	
	@GetMapping
    public ResponseEntity<List<BooksResponse>> getAllBooks() {
        return new ResponseEntity<>(service.getAllBooks(), HttpStatus.OK);
    }
	
	@GetMapping("/{id}")
    public ResponseEntity<BooksResponse> getBook(@PathVariable(name="id") Long id) {
        return new ResponseEntity<>(service.getBook(id), HttpStatus.OK);
    }
	
	@PutMapping
    public ResponseEntity<BooksResponse> updateBook(@RequestBody BooksRequest request) {
        return new ResponseEntity<>(service.updateBook(request), HttpStatus.OK);
    }

	@DeleteMapping
    public ResponseEntity<String> deleteBook(@RequestBody BooksRequest request) {
        return new ResponseEntity<>(service.deleteBook(request.getBookId()), HttpStatus.OK);
    }
}
