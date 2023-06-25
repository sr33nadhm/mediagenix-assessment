package com.mediagenix.assessment.exception.exceptions;

/**
 * A custom exception which will be thrown if a collection is not found.
 * CollectionNotFoundException is inheriting serialVersionUID from Serializable.
 * Added @SuppressWarnings to prevent warning.
 * */
@SuppressWarnings("serial")
public class CollectionNotFoundException extends RuntimeException{
	public CollectionNotFoundException(String s) {
        super(s);
    }
}
