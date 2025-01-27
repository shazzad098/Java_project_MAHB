package com.shazzad.librarysystem.service;

import com.shazzad.librarysystem.model.Book;
import java.util.ArrayList;
import java.util.List;

public class BookService {
    private List<Book> books;

    public BookService() {
        this.books = new ArrayList<>();
    }

    public void addBook(Book book) throws BookAlreadyExistsException {
        for (Book existingBook : books) {
            if (existingBook.getIsbn().equals(book.getIsbn())) {
                throw new BookAlreadyExistsException("Book with ISBN " + book.getIsbn() + " already exists.");
            }
        }
        books.add(book);
        System.out.println("Book added successfully: " + book.getTitle());
    }

    // **This is the missing method that you need to add:**
    public Book findBookByIsbn(String isbn) throws BookNotFoundException {
        for (Book book : books) {
            if (book.getIsbn().equals(isbn)) {
                return book;
            }
        }
        throw new BookNotFoundException("Book with ISBN " + isbn + " not found.");
    }

    public Book searchBookByIsbn(String isbn) {
        try {
            return findBookByIsbn(isbn); // Now this will call the findBookByIsbn method defined above
        } catch (BookNotFoundException e) {
            return null;
        }
    }

    public List<Book> searchBooksByTitle(String title) {
        List<Book> results = new ArrayList<>();
        for (Book book : books) {
            if (book.getTitle().toLowerCase().contains(title.toLowerCase())) {
                results.add(book);
            }
        }
        return results;
    }

    public List<Book> searchBooksByAuthor(String author) {
        List<Book> results = new ArrayList<>();
        for (Book book : books) {
            if (book.getAuthor().toLowerCase().contains(author.toLowerCase())) {
                results.add(book);
            }
        }
        return results;
    }

    public List<Book> searchBooksByGenre(String genre) {
        List<Book> results = new ArrayList<>();
        List<Book> searchResults = new ArrayList<>(); // You had two 'results' variables here, corrected to 'searchResults' for the inner list.
        for (Book book : books) {
            if (book.getGenre().toLowerCase().contains(genre.toLowerCase())) {
                searchResults.add(book);
            }
        }
        return searchResults; // Return the correct searchResults list
    }

    public List<Book> getAllBooks() {
        return books;
    }



    public class BookAlreadyExistsException extends Exception {
        public BookAlreadyExistsException(String message) {
            super(message);
        }
    }

    public class BookNotFoundException extends Exception {
        public BookNotFoundException(String message) {
            super(message);
        }
    }
}