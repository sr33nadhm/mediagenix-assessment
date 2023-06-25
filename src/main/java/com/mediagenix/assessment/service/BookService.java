package com.mediagenix.assessment.service;

import java.util.List;

import com.mediagenix.assessment.dto.BooksRequest;
import com.mediagenix.assessment.dto.BooksResponse;

public interface BookService {
	
	List<BooksResponse> getAllBooks();
	BooksResponse getBook(Long bookId);
	BooksResponse addBook(BooksRequest request);
	BooksResponse updateBook(BooksRequest request);
	String deleteBook(Long bookId);
}
