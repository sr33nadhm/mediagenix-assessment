package com.mediagenix.assessment.controller;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Objects;
import java.util.SortedSet;
import java.util.TreeSet;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.mediagenix.assessment.dto.BooksRequest;
import com.mediagenix.assessment.dto.BooksResponse;
import com.mediagenix.assessment.entity.BookEntity;
import com.mediagenix.assessment.entity.CollectionEntity;
import com.mediagenix.assessment.service.BookService;

@SpringBootTest
@AutoConfigureMockMvc
public class BooksControllerTest {

	@MockBean
	private BookService bookService;

	@Autowired
	private MockMvc mockMvc;

	@Autowired
	private ModelMapper modelMapper;

	@Autowired
	private ObjectMapper objectMapper;

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
	void addBookTest() throws Exception {

		BooksResponse bookResponse = modelMapper.map(bookEntity, BooksResponse.class);
		Mockito.when(bookService.addBook(Mockito.any())).thenReturn(bookResponse);

		this.mockMvc
				.perform(MockMvcRequestBuilders.post("/books").contentType(MediaType.APPLICATION_JSON)
						.content(Objects.requireNonNull(convertObjectToJsonString(bookRequest)))
						.accept(MediaType.APPLICATION_JSON))
				.andDo(print()).andExpect(status().isCreated()).andExpect(jsonPath("$.title").exists());

	}

	@Test
	void updateBookTest() throws Exception {

		BooksResponse bookResponse = modelMapper.map(bookEntity, BooksResponse.class);
		Mockito.when(bookService.updateBook(Mockito.any())).thenReturn(bookResponse);

		this.mockMvc
				.perform(MockMvcRequestBuilders.put("/books").contentType(MediaType.APPLICATION_JSON)
						.content(Objects.requireNonNull(convertObjectToJsonString(bookRequest)))
						.accept(MediaType.APPLICATION_JSON))
				.andDo(print()).andExpect(status().isOk()).andExpect(jsonPath("$.title").exists());

	}

	@Test
	void getBookTest() throws Exception {

		long bookId = 1;
		BooksResponse bookResponse = modelMapper.map(bookEntity, BooksResponse.class);
		Mockito.when(bookService.getBook(Mockito.anyLong())).thenReturn(bookResponse);

		this.mockMvc.perform(MockMvcRequestBuilders.get("/books/" + bookId).contentType(MediaType.APPLICATION_JSON)
				.content(Objects.requireNonNull(convertObjectToJsonString(bookId))).accept(MediaType.APPLICATION_JSON))
				.andDo(print()).andExpect(status().isOk()).andExpect(jsonPath("$.title").exists());
	}

	@Test
	void getAllBooksTest() throws Exception {
		List<BooksResponse> booksList = new ArrayList<>();
		booksList.add(modelMapper.map(bookEntity, BooksResponse.class));
		Mockito.when(bookService.getAllBooks()).thenReturn(booksList);

		this.mockMvc.perform(MockMvcRequestBuilders.get("/books").contentType(MediaType.APPLICATION_JSON)
				.accept(MediaType.APPLICATION_JSON)).andDo(print()).andExpect(status().isOk());

	}

	@Test
	void deleteBookTest() throws Exception {

		long bookId = 1;
		String deleteResponse = "Deleted book with id " + bookId;
		Mockito.when(bookService.deleteBook(Mockito.any())).thenReturn(deleteResponse);

		this.mockMvc
				.perform(MockMvcRequestBuilders.delete("/books").contentType(MediaType.APPLICATION_JSON)
						.content(Objects.requireNonNull(convertObjectToJsonString(bookRequest)))
						.accept(MediaType.APPLICATION_JSON))
				.andDo(print()).andExpect(status().isOk()).andExpect(result -> deleteResponse.toString());
	}

	private String convertObjectToJsonString(Object o) {
		try {
			return objectMapper.writeValueAsString(o);
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}
}
