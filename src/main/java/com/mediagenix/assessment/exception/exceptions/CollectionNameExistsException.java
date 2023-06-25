package com.mediagenix.assessment.exception.exceptions;

/**
 * A custom exception which will be thrown if a collection is not found.
 * CollectionExistsException is inheriting serialVersionUID from Serializable.
 * Added @SuppressWarnings to prevent warning.
 * */
@SuppressWarnings("serial")
public class CollectionNameExistsException extends RuntimeException{
	public CollectionNameExistsException(String s) {
        super(s);
    }
}
