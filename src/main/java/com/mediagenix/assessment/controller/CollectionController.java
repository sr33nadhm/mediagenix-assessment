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

import com.mediagenix.assessment.dto.CollectionRequest;
import com.mediagenix.assessment.dto.CollectionResponse;
import com.mediagenix.assessment.service.CollectionService;

@RestController
@RequestMapping("/collections")
public class CollectionController {

	@Autowired
    private CollectionService service;

    @PostMapping
    public ResponseEntity<CollectionResponse> addCollection(@RequestBody CollectionRequest request) {
        return new ResponseEntity<>(service.addCollection(request), HttpStatus.CREATED);
    }
    
    @PostMapping("/add")
    public ResponseEntity<String> addABook(@RequestBody CollectionRequest request) {
        return new ResponseEntity<>(service.addBook(request), HttpStatus.OK);
    }
    
    @PostMapping("/remove")
    public ResponseEntity<String> removeABook(@RequestBody CollectionRequest request) {
        return new ResponseEntity<>(service.removeBook(request), HttpStatus.OK);
    }
    
    @GetMapping
    public ResponseEntity<List<CollectionResponse>> getAllCollection() {
        return new ResponseEntity<>(service.getAllCollections(), HttpStatus.OK);
    }

    @GetMapping("/{name}")
    public ResponseEntity<CollectionResponse> getCollection(@PathVariable(name="name") String name) {
        return new ResponseEntity<>(service.getCollection(name), HttpStatus.OK);
    }
    
    @PutMapping
    public ResponseEntity<CollectionResponse> updateCollection(@RequestBody CollectionRequest request) {
        return new ResponseEntity<>(service.updateCollection(request), HttpStatus.OK);
    }
    
    @DeleteMapping
    public ResponseEntity<String> deleteCollection(@RequestBody CollectionRequest request) {
        return new ResponseEntity<>(service.deleteCollection(request.getCollectionId()), HttpStatus.OK);
    }
}
