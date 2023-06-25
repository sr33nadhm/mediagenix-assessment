package com.mediagenix.assessment.service.impl;

import java.util.Date;
import java.util.Optional;
import java.util.SortedSet;
import java.util.TreeSet;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import com.mediagenix.assessment.dto.BooksRequest;
import com.mediagenix.assessment.dto.BooksResponse;
import com.mediagenix.assessment.entity.BookEntity;
import com.mediagenix.assessment.entity.CollectionEntity;
import com.mediagenix.assessment.repository.BookRepository;
import com.mediagenix.assessment.service.BookService;

@SpringBootTest
public class BookServiceImplTest {

	@Autowired
	private BookService bookService;

	@MockBean
	private BookRepository bookRepo;
	
	@Autowired
	private ModelMapper modelMapper;

	BooksRequest bookRequest;

	BookEntity bookEntity;
	
	@BeforeEach
	void setUp() {
		bookRequest = new BooksRequest(1, "ISBN-13 978-3-642-11746-0", "HP 5", "HP - author5");
		SortedSet<CollectionEntity> collections = new TreeSet<>();
		bookEntity = new BookEntity(1, "ISBN-13 978-3-642-11746-0", "HP 5", "HP - author5", collections, new Date(),
				new Date());
	}

	@AfterEach
	void tearDown() {

	}
	
	@Test
	void createBookTest() {
		Mockito.when(bookRepo.save(Mockito.any())).thenReturn(bookEntity);
		BooksResponse booksResponse = bookService
				.addBook(modelMapper.map(bookEntity, BooksRequest.class));

		Assertions.assertNotNull(booksResponse);
		Assertions.assertEquals(bookEntity.getISBN(), booksResponse.getISBN());
	}

	@Test
	void deleteBookTest() {
		long bookId = 1;
		Mockito.when(bookRepo.findById(Mockito.anyLong())).thenReturn(Optional.of(bookEntity));
		String response = bookService.deleteBook(bookId);

		Assertions.assertNotNull(response);
		Assertions.assertEquals("Deleted book with id " + bookId, response);

	}
}
