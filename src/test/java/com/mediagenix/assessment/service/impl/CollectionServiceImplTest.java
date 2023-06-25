package com.mediagenix.assessment.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
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
import com.mediagenix.assessment.dto.CollectionRequest;
import com.mediagenix.assessment.dto.CollectionResponse;
import com.mediagenix.assessment.entity.BookEntity;
import com.mediagenix.assessment.entity.CollectionEntity;
import com.mediagenix.assessment.repository.CollectionRepository;
import com.mediagenix.assessment.service.CollectionService;

//import org.springframework.data.domain.Sort;

@SpringBootTest
public class CollectionServiceImplTest {
	@Autowired
	private CollectionService collectionService;

	@MockBean
	private CollectionRepository collectionRepo;

	@Autowired
	private ModelMapper modelMapper;

	CollectionRequest collectionRequest;

	CollectionEntity collectionEntity;

	SortedSet<BookEntity> books = new TreeSet<>();

	List<BooksRequest> booksList = new ArrayList<>();

	@BeforeEach
	void setUp() {
		collectionRequest = new CollectionRequest(1L, "Collection-1", booksList);
		collectionEntity = new CollectionEntity(1L, "Collection-1", books, new Date(), new Date());
	}

	@AfterEach
	void tearDown() {

	}

	@Test
	void createCollectionTest() {
		Mockito.when(collectionRepo.save(Mockito.any())).thenReturn(collectionEntity);
		CollectionResponse collectionResponse = collectionService
				.addCollection(modelMapper.map(collectionEntity, CollectionRequest.class));

		Assertions.assertNotNull(collectionResponse);
		Assertions.assertEquals(collectionEntity.getName(), collectionResponse.getName());
	}

	@Test
	void deleteCollectionTest() {
		long collectionId = 1;
		Mockito.when(collectionRepo.findById(Mockito.anyLong())).thenReturn(Optional.of(collectionEntity));
		String response = collectionService.deleteCollection(collectionId);

		Assertions.assertNotNull(response);
		Assertions.assertEquals("Deleted collection with id " + collectionId, response);

	}

}
