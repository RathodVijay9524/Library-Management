package com.vijay.book_managment.controller;

import com.vijay.book_managment.entity.Book;
import com.vijay.book_managment.model.BookRequest;
import com.vijay.book_managment.model.BookResponse;
import com.vijay.book_managment.service.BookService;
import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Set;
import java.util.concurrent.CompletableFuture;

@RestController
@RequestMapping("/api/books")
@AllArgsConstructor
@Log4j2
public class BookController {
    private final BookService bookService;

    // Endpoint to create a new book
    @PostMapping
    public CompletableFuture<ResponseEntity<BookResponse>> createBook(@RequestBody BookRequest bookRequest) {
        log.info("Received request to create a book with title: {}", bookRequest.getTitle());
        return bookService.create(bookRequest)
                .thenApply(bookResponse -> {
                    log.info("Successfully created book with ID: {}", bookResponse.getId());
                    return ResponseEntity.status(HttpStatus.CREATED).body(bookResponse);
                });
    }

    // Endpoint to get a book by ID
    @GetMapping("/{id}")
    public CompletableFuture<ResponseEntity<BookResponse>> getBookById(@PathVariable Long id) {
        log.info("Received request to get book with ID: {}", id);
        return bookService.getById(id)
                .thenApply(ResponseEntity::ok);

    }

    // Endpoint to get all books
    @GetMapping
    public CompletableFuture<ResponseEntity<Set<BookResponse>>> getAllBooks() {
        log.info("Received request to get all books");
        return bookService.getAll()
                .thenApply(ResponseEntity::ok)
                .exceptionally(exception -> {
                    log.error("Failed to get all books: {}", exception.getMessage());
                    return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
                });
    }

    // Update an existing book
    @PutMapping("/{id}")
    public CompletableFuture<ResponseEntity<BookResponse>> updateBook(@PathVariable Long id, @RequestBody BookRequest request) {
        return bookService.update(id, request)
                .thenApply(bookResponse -> new ResponseEntity<>(bookResponse, HttpStatus.OK));

    }

    // Delete a book
    @DeleteMapping("/{id}")
    public CompletableFuture<ResponseEntity<Void>> deleteBook(@PathVariable Long id) {
        return bookService.delete(id)
                .thenApply(voidResult -> new ResponseEntity<Void>(HttpStatus.NO_CONTENT));

    }

    @GetMapping("/author/{authorId}")
    public CompletableFuture<ResponseEntity<List<BookResponse>>> getBooksByAuthorId(@PathVariable Long authorId) {
        return bookService.getBooksByAuthorId(authorId)
                .thenApply(books -> ResponseEntity.ok().body(books));

    }

}
