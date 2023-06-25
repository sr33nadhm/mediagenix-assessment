package com.mediagenix.assessment.exception.exceptions;

/**
 * A custom exception which will be thrown if a book is not found.
 * BookNotFoundException is inheriting serialVersionUID from Serializable.
 * Added @SuppressWarnings to prevent warning.
 * */
@SuppressWarnings("serial")
public class BookNotFoundException extends RuntimeException{
	public BookNotFoundException(String s) {
        super(s);
    }
}
