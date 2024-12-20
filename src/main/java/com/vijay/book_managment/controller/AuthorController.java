package com.vijay.book_managment.controller;


import com.vijay.book_managment.model.AuthorRequest;
import com.vijay.book_managment.model.AuthorResponse;
import com.vijay.book_managment.service.AuthorService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Set;
import java.util.concurrent.CompletableFuture;

@RestController
@RequestMapping("/api/authors")
@AllArgsConstructor
public class AuthorController {

    private final AuthorService authorService;

    // Endpoint to create an author
    @PostMapping
    public CompletableFuture<ResponseEntity<AuthorResponse>> create(@RequestBody AuthorRequest request) {
        return authorService.create(request)
                .thenApply(authorResponse -> ResponseEntity.status(HttpStatus.CREATED).body(authorResponse))
                .exceptionally(exception -> {
                    // Handle failure and return internal server error
                    return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
                });
    }

    // Endpoint to get an author by ID
    @GetMapping("/{id}")
    public CompletableFuture<ResponseEntity<AuthorResponse>> getById(@PathVariable Long id) {
        return authorService.getById(id)
                .thenApply(authorResponse -> ResponseEntity.ok(authorResponse))
                .exceptionally(exception -> {
                    // Handle failure and return 404 Not Found if author is not found
                    return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
                });
    }

    // Endpoint to get all authors
    @GetMapping
    public CompletableFuture<ResponseEntity<Set<AuthorResponse>>> getAll() {
        return authorService.getAll()
                .thenApply(authorResponses -> ResponseEntity.ok(authorResponses))
                .exceptionally(exception -> {
                    // Handle failure and return internal server error
                    return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
                });
    }

    // Endpoint to update an author
    @PutMapping("/{id}")
    public CompletableFuture<ResponseEntity<AuthorResponse>> update(@PathVariable Long id, @RequestBody AuthorRequest request) {
        return authorService.update(id, request)
                .thenApply(authorResponse -> ResponseEntity.ok(authorResponse))
                .exceptionally(exception -> {
                    // Handle failure and return internal server error
                    // Log exception here (optional)
                    return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
                });
    }

    // Endpoint to delete an author
    @DeleteMapping("/{id}")
    public CompletableFuture<ResponseEntity<Void>> deleteBook(@PathVariable Long id) {
        return authorService.delete(id)
                .thenApply(voidResult -> new ResponseEntity<Void>(HttpStatus.NO_CONTENT))
                .exceptionally(exception -> {
                    // Log exception here (optional)
                    return new ResponseEntity<>(HttpStatus.NOT_FOUND);
                });
    }
}

