package com.mediagenix.assessment.service.impl;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.mediagenix.assessment.dto.BooksRequest;
import com.mediagenix.assessment.dto.BooksResponse;
import com.mediagenix.assessment.entity.BookEntity;
import com.mediagenix.assessment.entity.CollectionEntity;
import com.mediagenix.assessment.exception.exceptions.BookNotFoundException;
import com.mediagenix.assessment.repository.BookRepository;
import com.mediagenix.assessment.service.BookService;

@Service
public class BookServiceImpl implements BookService {

	@Autowired
	private ModelMapper modelMapper;

	@Autowired
	private BookRepository bookRepo;

	@Override
	public BooksResponse addBook(BooksRequest request) {
		BookEntity bookEntity = BookEntity.builder().author(request.getAuthor()).ISBN(request.getISBN())
				.title(request.getTitle()).build();

		BookEntity savedBookEntity = bookRepo.save(bookEntity);
		return modelMapper.map(savedBookEntity, BooksResponse.class);
	}

	@Override
	public List<BooksResponse> getAllBooks() {
		return bookRepo.findAll().stream().map(bookEntity -> modelMapper.map(bookEntity, BooksResponse.class))
				.collect(Collectors.toList());
	}

	@Override
	public BooksResponse getBook(Long bookId) {
		BookEntity bookEntity = bookRepo.findById(bookId)
				.orElseThrow(() -> new BookNotFoundException("No book found with id " + bookId));
		return modelMapper.map(bookEntity, BooksResponse.class);

	}

	@Override
	public BooksResponse updateBook(BooksRequest request) {
		BookEntity bookEntity = bookRepo.findById(request.getBookId())
				.orElseThrow(() -> new BookNotFoundException("No book found with id " + request.getBookId()));

		modelMapper.map(request, bookEntity);
		BookEntity savedBookEntity = bookRepo.save(bookEntity);
		return modelMapper.map(savedBookEntity, BooksResponse.class);
	}

	@Override
	public String deleteBook(Long bookId) {
		BookEntity bookEntity = bookRepo.findById(bookId)
				.orElseThrow(() -> new BookNotFoundException("No book found with id " + bookId));
		Set<CollectionEntity> collections = bookEntity.getCollections().stream().collect(Collectors.toSet());
		collections.forEach(collection -> {
			bookEntity.removeAttachedCollections(collection);
		});
		bookRepo.delete(bookEntity);
		return "Deleted book with id " + bookId;
	}

}
