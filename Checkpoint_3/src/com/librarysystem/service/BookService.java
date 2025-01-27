package com.librarysystem.service;

import com.librarysystem.model.Book;
import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class BookService {
    private List<Book> books;
    private static final String BOOKS_FILE_PATH = "data/books.txt"; // Path to books data file

    public BookService() {
        this.books = new ArrayList<>();
        loadBooksFromFile();
    }

    private void loadBooksFromFile() {
        try (BufferedReader reader = new BufferedReader(new FileReader(BOOKS_FILE_PATH))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(",");
                if (parts.length == 4) {
                    String title = parts[0].trim();
                    String author = parts[1].trim();
                    String genre = parts[2].trim();
                    String isbn = parts[3].trim();
                    books.add(new Book(title, author, genre, isbn));
                } else {
                    System.err.println("Skipping invalid book data line: " + line);
                }
            }
        } catch (FileNotFoundException e) {
            System.out.println("Books data file not found. Starting with empty book list.");
        } catch (IOException e) {
            System.err.println("Error reading books from file: " + e.getMessage());
        }
    }

    public void saveBooksToFile() {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(BOOKS_FILE_PATH))) {
            for (Book book : books) {
                String bookData = String.format("%s,%s,%s,%s",
                        book.getTitle(),
                        book.getAuthor(),
                        book.getGenre(),
                        book.getIsbn());
                writer.write(bookData);
                writer.newLine();
            }
        } catch (IOException e) {
            System.err.println("Error saving books to file: " + e.getMessage());
        }
    }

    public void addBook(Book book) throws BookAlreadyExistsException {
        for (Book existingBook : books) {
            if (existingBook.getIsbn().equals(book.getIsbn())) {
                throw new BookAlreadyExistsException("Book with ISBN " + book.getIsbn() + " already exists.");
            }
        }
        books.add(book);
        saveBooksToFile();
        System.out.println("Book added successfully: " + book.getTitle());
    }

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
            return findBookByIsbn(isbn);
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
        List<Book> searchResults = new ArrayList<>();
        for (Book book : books) {
            if (book.getGenre().toLowerCase().contains(genre.toLowerCase())) {
                searchResults.add(book);
            }
        }
        return searchResults;
    }

    public List<Book> getAllBooks() {
        return books;
    }
}