package com.polarbookshop.catalog_service.domain;

public class BookNotFoundException extends RuntimeException {
    public BookNotFoundException(String isbn) {
        super("A book with ISBN " + isbn + " was not found.");
    }
}