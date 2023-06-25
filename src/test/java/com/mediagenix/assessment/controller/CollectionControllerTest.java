package com.mediagenix.assessment.controller;

import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

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

import com.fasterxml.jackson.databind.ObjectMapper;
import com.mediagenix.assessment.dto.BooksRequest;
import com.mediagenix.assessment.dto.CollectionRequest;
import com.mediagenix.assessment.dto.CollectionResponse;
import com.mediagenix.assessment.entity.BookEntity;
import com.mediagenix.assessment.entity.CollectionEntity;
import com.mediagenix.assessment.service.CollectionService;

@SpringBootTest
@AutoConfigureMockMvc
public class CollectionControllerTest {

	@MockBean
	private CollectionService collectionService;

	@Autowired
	private MockMvc mockMvc;

	@Autowired
	private ModelMapper modelMapper;

	@Autowired
	private ObjectMapper objectMapper;

	CollectionRequest collectionRequest;

	CollectionEntity collectionEntity;
	
	SortedSet<BookEntity> books = new TreeSet<>();

	@BeforeEach
	void setUp() {
		List<BooksRequest> booksList = new ArrayList<>();
		collectionRequest = new CollectionRequest(1L, "Collection-1", booksList);
		collectionEntity = new CollectionEntity(1L, "Collection-1", books, new Date(), new Date());
	}

	@AfterEach
	void tearDown() {
		collectionEntity.setBooks(books);
	}
	
	@Test
	void addCollectionTest() throws Exception {
		CollectionResponse collectionResponse = modelMapper.map(collectionEntity, CollectionResponse.class);
		Mockito.when(collectionService.addCollection(Mockito.any())).thenReturn(collectionResponse);

		this.mockMvc
				.perform(MockMvcRequestBuilders.post("/collections").contentType(MediaType.APPLICATION_JSON)
						.content(Objects.requireNonNull(convertObjectToJsonString(collectionRequest)))
						.accept(MediaType.APPLICATION_JSON))
				.andDo(print()).andExpect(status().isCreated()).andExpect(jsonPath("$.name").exists());

	}
	
	@Test
	void addABookToCollectionTest() throws Exception {
		SortedSet<BookEntity> newBooks = new TreeSet<>();
		BookEntity bookEntity;
		SortedSet<CollectionEntity> collections = new TreeSet<>();
		bookEntity = new BookEntity(2, "ISBN-13 978-3-642-11746-0", "HP 5", "HP - author5", collections, new Date(),
				new Date());
		newBooks.add(bookEntity);
		collectionEntity.setBooks(newBooks);
		
		String collectionResponse = "Added " + bookEntity.getTitle() + " to " + collectionEntity.getName();
		Mockito.when(collectionService.addBook(Mockito.any())).thenReturn(collectionResponse);

		this.mockMvc
				.perform(MockMvcRequestBuilders.post("/collections/add").contentType(MediaType.APPLICATION_JSON)
						.content(Objects.requireNonNull(convertObjectToJsonString(collectionRequest)))
						.accept(MediaType.APPLICATION_JSON))
				.andDo(print()).andExpect(status().isOk()).andExpect(result -> collectionResponse.toString());

	}
	
	@Test
	void removeBookFromCollectionTest() throws Exception {
		SortedSet<BookEntity> newBooks = new TreeSet<>();
		BookEntity bookEntity;
		SortedSet<CollectionEntity> collections = new TreeSet<>();
		bookEntity = new BookEntity(2, "ISBN-13 978-3-642-11746-0", "HP 5", "HP - author5", collections, new Date(),
				new Date());
		newBooks.add(bookEntity);
		collectionEntity.setBooks(newBooks);
		
		String collectionResponse = "Removed " + bookEntity.getTitle() + " from " + collectionEntity.getName();
		Mockito.when(collectionService.removeBook(Mockito.any())).thenReturn(collectionResponse);

		this.mockMvc
				.perform(MockMvcRequestBuilders.post("/collections/remove").contentType(MediaType.APPLICATION_JSON)
						.content(Objects.requireNonNull(convertObjectToJsonString(collectionRequest)))
						.accept(MediaType.APPLICATION_JSON))
				.andDo(print()).andExpect(status().isOk()).andExpect(result -> collectionResponse.toString());

	}
	
	@Test
	void updateCollectionTest() throws Exception {
		CollectionResponse collectionResponse = modelMapper.map(collectionEntity, CollectionResponse.class);
		Mockito.when(collectionService.updateCollection(Mockito.any())).thenReturn(collectionResponse);

		this.mockMvc
				.perform(MockMvcRequestBuilders.put("/collections").contentType(MediaType.APPLICATION_JSON)
						.content(Objects.requireNonNull(convertObjectToJsonString(collectionRequest)))
						.accept(MediaType.APPLICATION_JSON))
				.andDo(print()).andExpect(status().isOk()).andExpect(jsonPath("$.name").exists());

	}
	
	@Test
	void getCollectionTest() throws Exception {
		String collectionName = "Collection-1";
		CollectionResponse collectionResponse = modelMapper.map(collectionEntity, CollectionResponse.class);
		Mockito.when(collectionService.getCollection(Mockito.anyString())).thenReturn(collectionResponse);
		this.mockMvc.perform(MockMvcRequestBuilders.get("/collections/" + collectionName).contentType(MediaType.APPLICATION_JSON)
				.content(Objects.requireNonNull(convertObjectToJsonString(collectionName))).accept(MediaType.APPLICATION_JSON))
				.andDo(print()).andExpect(status().isOk()).andExpect(jsonPath("$.name").exists());
	}
	
	@Test
	void getAllCollectionsTest() throws Exception {
		List<CollectionResponse> collectionsList = new ArrayList<>();
		collectionsList.add(modelMapper.map(collectionEntity, CollectionResponse.class));
		Mockito.when(collectionService.getAllCollections()).thenReturn(collectionsList);

		this.mockMvc.perform(MockMvcRequestBuilders.get("/collections").contentType(MediaType.APPLICATION_JSON)
				.accept(MediaType.APPLICATION_JSON)).andDo(print()).andExpect(status().isOk());

	}
	
	@Test
	void deleteCollectionTest() throws Exception {
		long collectionId = 1;
		String deleteResponse = "Deleted collection with id " + collectionId;
		Mockito.when(collectionService.deleteCollection(Mockito.any())).thenReturn(deleteResponse);

		this.mockMvc
				.perform(MockMvcRequestBuilders.delete("/collections").contentType(MediaType.APPLICATION_JSON)
						.content(Objects.requireNonNull(convertObjectToJsonString(collectionRequest)))
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
