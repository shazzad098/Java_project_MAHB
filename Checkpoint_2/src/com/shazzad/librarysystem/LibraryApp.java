package com.shazzad.librarysystem;

import com.shazzad.librarysystem.model.Book;
import com.shazzad.librarysystem.model.User;
import com.shazzad.librarysystem.service.BookAlreadyExistsException;
import com.shazzad.librarysystem.service.BookService;
import com.shazzad.librarysystem.service.UserNotFoundException;
import com.shazzad.librarysystem.service.UserService;
import com.shazzad.librarysystem.util.InputHandler;

import java.util.List;

public class LibraryApp {
    private static UserService userService = new UserService();
    private static BookService bookService = new BookService();

    public static void main(String[] args) {
        displayWelcomeMessage();
        mainMenu();
    }

    private static void displayWelcomeMessage() {
        System.out.println("-----------------------------------------");
        System.out.println("Welcome to the Library Management System!");
        System.out.println("-----------------------------------------");
    }

    private static void mainMenu() {
        boolean running = true;
        while (running) {
            displayMenuOptions();
            int choice = InputHandler.getIntInput("Enter your choice");

            switch (choice) {
                case 1:
                    addBook();
                    break;
                case 2:
                    searchBooksByTitle();
                    break;
                case 3:
                    searchBookByIsbn();
                    break;
                case 4:
                    listAllBooks();
                    break;
                case 5:
                    listAllUsers(); // Call existing listAllUsers() method for option 5
                    break;
                case 0:
                    System.out.println("Exiting Library Management System. Goodbye!");
                    running = false;
                    break;
                default:
                    System.out.println("Invalid choice. Please try again.");
            }
            System.out.println();
        }
    }

    private static void displayMenuOptions() {
        System.out.println("\n--- Main Menu ---");
        System.out.println("1. Add Book");
        System.out.println("2. Search Books (by Title)");
        System.out.println("3. Search Book (by ISBN)");
        System.out.println("4. List All Books");
        System.out.println("5. View Registered Users"); // New option
        System.out.println("0. Exit");
    }

    private static void addBook() {
        System.out.println("\n--- Add New Book ---");
        String title = InputHandler.getStringInput("Enter book title");
        String author = InputHandler.getStringInput("Enter book author");
        String genre = InputHandler.getStringInput("Enter book genre");
        String isbn = InputHandler.getStringInput("Enter book ISBN");

        Book newBook = new Book(title, author, genre, isbn);
        try {
            bookService.addBook(newBook);
        } catch (BookService.BookAlreadyExistsException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    private static void searchBooksByTitle() {
        System.out.println("\n--- Search Books by Title ---");
        String searchTitle = InputHandler.getStringInput("Enter title to search for");
        List<Book> results = bookService.searchBooksByTitle(searchTitle);
        if (results.isEmpty()) {
            System.out.println("No books found matching title: " + searchTitle);
        } else {
            System.out.println("--- Search Results ---");
            for (Book book : results) {
                System.out.println(book);
            }
        }
    }
    private static void searchBookByIsbn() {
        System.out.println("\n--- Search Book by ISBN ---");
        String isbnToSearch = InputHandler.getStringInput("Enter ISBN to search for");
        Book foundBook = bookService.searchBookByIsbn(isbnToSearch);

        if (foundBook != null) {
            System.out.println("--- Book Found ---");
            System.out.println(foundBook);
        } else {
            System.out.println("No book found with ISBN: " + isbnToSearch);
        }
    }

    private static void listAllBooks() {
        System.out.println("\n--- All Books in Library ---");
        List<Book> allBooks = bookService.getAllBooks();
        if (allBooks.isEmpty()) {
            System.out.println("No books in the library yet.");
        } else {
            for (Book book : allBooks) {
                System.out.println(book);
            }
        }
    }

    private static void listAllUsers() {
        System.out.println("\n--- Registered Users ---");
        List<User> allUsers = userService.getAllUsers();
        for (User user : allUsers) {
            System.out.println(user);
        }
    }
}