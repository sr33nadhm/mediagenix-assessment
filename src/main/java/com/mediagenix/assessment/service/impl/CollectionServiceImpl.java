package com.mediagenix.assessment.service.impl;

import java.util.List;
import java.util.Set;
import java.util.SortedSet;
import java.util.TreeSet;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.mediagenix.assessment.dto.BooksRequest;
import com.mediagenix.assessment.dto.CollectionRequest;
import com.mediagenix.assessment.dto.CollectionResponse;
import com.mediagenix.assessment.entity.BookEntity;
import com.mediagenix.assessment.entity.CollectionEntity;
import com.mediagenix.assessment.exception.exceptions.BookExistsException;
import com.mediagenix.assessment.exception.exceptions.BookNotFoundException;
import com.mediagenix.assessment.exception.exceptions.CollectionNotFoundException;
import com.mediagenix.assessment.repository.BookRepository;
import com.mediagenix.assessment.repository.CollectionRepository;
import com.mediagenix.assessment.service.BookService;
import com.mediagenix.assessment.service.CollectionService;

@Service
public class CollectionServiceImpl implements CollectionService {

	@Autowired
	private ModelMapper modelMapper;

	@Autowired
	private CollectionRepository collectionRepo;

	@Autowired
	private BookRepository bookRepo;

	@Autowired
	private BookService bookService;

	private static final String BOOK_NOT_FOUND = "No book found with id ";
	private static final String COLLECTION_NOT_FOUND_WITH_ID = "No collection found with id ";
	private static final String COLLECTION_NOT_FOUND_WITH_NAME = "Collection found with name ";

	@Override
	public CollectionResponse addCollection(CollectionRequest request) {
		Boolean duplicateCollection = collectionRepo.findByName(request.getName()).isPresent();
		if (duplicateCollection)
			throw new CollectionNotFoundException(COLLECTION_NOT_FOUND_WITH_NAME + request.getName());

		List<BooksRequest> bookInRequest = request.getBooks();
		SortedSet<BookEntity> bookEntities = new TreeSet<>();
		bookInRequest.forEach(book -> {
			BookEntity bookEntity = BookEntity.builder().author(book.getAuthor()).ISBN(book.getISBN())
					.title(book.getTitle()).build();
			bookEntities.add(bookRepo.save(bookEntity));
		});

		CollectionEntity collectionEntity = CollectionEntity.builder().name(request.getName()).books(bookEntities)
				.build();

		CollectionEntity savedCollectionEntity = collectionRepo.save(collectionEntity);
		return modelMapper.map(savedCollectionEntity, CollectionResponse.class);
	}

	@Override
	public List<CollectionResponse> getAllCollections() {
		return collectionRepo.findAll().stream()
				.map(collectionEntity -> modelMapper.map(collectionEntity, CollectionResponse.class))
				.collect(Collectors.toList());
	}

	@Override
	public CollectionResponse getCollection(String name) {
		CollectionEntity collectionEntity = collectionRepo.findByName(name)
				.orElseThrow(() -> new CollectionNotFoundException(COLLECTION_NOT_FOUND_WITH_NAME + name));
		return modelMapper.map(collectionEntity, CollectionResponse.class);
	}

	@Override
	public CollectionResponse updateCollection(CollectionRequest request) {
		CollectionEntity collectionEntity = collectionRepo.findById(request.getCollectionId()).orElseThrow(
				() -> new CollectionNotFoundException(COLLECTION_NOT_FOUND_WITH_ID + request.getCollectionId()));

		collectionEntity.setName(request.getName());
		request.getBooks().forEach(book -> {
			if (book.getBookId() > 0) {
				bookService.updateBook(book);
			} else {
				bookService.addBook(book);
			}
		});
		CollectionEntity savedBookEntity = collectionRepo.save(collectionEntity);
		return modelMapper.map(savedBookEntity, CollectionResponse.class);
	}

	@Override
	public String deleteCollection(Long collectionId) {
		CollectionEntity collectionEntity = collectionRepo.findById(collectionId)
				.orElseThrow(() -> new CollectionNotFoundException(COLLECTION_NOT_FOUND_WITH_ID + collectionId));
		for (BookEntity book : collectionEntity.getBooks()) {
			collectionEntity.removeBook(book);
		}
		collectionRepo.delete(collectionEntity);
		return "Deleted collection with id " + collectionId;
	}

	@Override
	public String addBook(CollectionRequest request) {
		CollectionEntity collectionEntity = collectionRepo.findByName(request.getName()).orElseThrow(
				() -> new CollectionNotFoundException(COLLECTION_NOT_FOUND_WITH_NAME + request.getName()));
		BookEntity bookInfo = modelMapper.map(request.getBooks().get(0), BookEntity.class);
		BookEntity book = bookRepo.findById(bookInfo.getBookId())
				.orElseThrow(() -> new BookNotFoundException(BOOK_NOT_FOUND + bookInfo.getBookId()));
		Set<BookEntity> books = collectionEntity.getBooks();
		if (books.contains(book)) {
			throw new BookExistsException("Book exists with id " + bookInfo.getBookId());
		}
		collectionEntity.addBook(book);
		collectionRepo.save(collectionEntity);
		return "Added " + book.getTitle() + " to " + request.getName();
	}

	@Override
	public String removeBook(CollectionRequest request) {
		CollectionEntity collectionEntity = collectionRepo.findByName(request.getName()).orElseThrow(
				() -> new CollectionNotFoundException(COLLECTION_NOT_FOUND_WITH_NAME + request.getName()));
		BookEntity bookInfo = modelMapper.map(request.getBooks().get(0), BookEntity.class);
		BookEntity book = bookRepo.findById(bookInfo.getBookId())
				.orElseThrow(() -> new BookNotFoundException(BOOK_NOT_FOUND + bookInfo.getBookId()));
		Set<BookEntity> books = collectionEntity.getBooks();
		if (books.contains(book)) {
			collectionEntity.removeBook(book);
			collectionRepo.save(collectionEntity);
			return "Removed " + book.getTitle() + " from " + request.getName();
		} else {
			throw new BookNotFoundException(BOOK_NOT_FOUND + bookInfo.getBookId() + " in " + request.getName());
		}

	}

}
