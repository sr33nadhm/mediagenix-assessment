package com.mediagenix.assessment.service;

import java.util.List;

import com.mediagenix.assessment.dto.CollectionRequest;
import com.mediagenix.assessment.dto.CollectionResponse;

public interface CollectionService {
	List<CollectionResponse> getAllCollections();
	CollectionResponse getCollection(String name);
	CollectionResponse addCollection(CollectionRequest request);
	CollectionResponse updateCollection(CollectionRequest request);
	String deleteCollection(Long collectionId);
	String addBook(CollectionRequest request);
	String removeBook(CollectionRequest request);
}
