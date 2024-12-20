package com.vijay.book_managment.service.impl;

import com.vijay.book_managment.entity.Author;
import com.vijay.book_managment.entity.Book;
import com.vijay.book_managment.exception.ResourceNotFoundException;
import com.vijay.book_managment.model.BookRequest;
import com.vijay.book_managment.model.BookResponse;
import com.vijay.book_managment.repository.AuthorRepository;
import com.vijay.book_managment.repository.BookRepository;
import com.vijay.book_managment.service.BookService;
import com.vijay.book_managment.util.Mapper;
import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;


import java.util.List;
import java.util.Set;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Executor;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
@Log4j2
public class BookServiceIml implements BookService {
    private final BookRepository bookRepository;
    private final AuthorRepository authorRepository;
    private final Executor executor; // Executor injected by Spring

    @Override
    public CompletableFuture<BookResponse> create(BookRequest request) {
        log.info("Creating a new book with title: {}", request.getTitle());
        return CompletableFuture.supplyAsync(() -> {
            Author author = authorRepository.findById(request.getAuthorId())
                    .orElseThrow(() -> new ResourceNotFoundException("Author", "id", request.getAuthorId()));
            Book book = Mapper.toEntity(request, Book.class);
            book.setAuthor(author);
            bookRepository.save(book);
            return Mapper.toDto(book,BookResponse.class);
        }, executor);
    }

    @Override
    public CompletableFuture<BookResponse> getById(Long id) {
        log.info("Fetching book with ID: {}", id);
        return CompletableFuture.supplyAsync(() -> {
            Book book = bookRepository.findById(id)
                    .orElseThrow(() -> new ResourceNotFoundException("Book", "id", id));
            return Mapper.toDto(book, BookResponse.class);
        },executor);
    }

        @Override
        public CompletableFuture<Set<BookResponse>> getAll () {
            log.info("Fetching all books");
            return CompletableFuture.supplyAsync(() -> bookRepository.findAll().stream()
                            .map(book -> Mapper.toDto(book, BookResponse.class))
                            .collect(Collectors.toSet()), executor)
                    .thenApply(books -> {
                        log.info("Fetched {} books", books.size());
                        return books;
                    })
                    .exceptionally(exception -> {
                        log.error("Failed to get all books: {}", exception.getMessage());
                        throw new RuntimeException("Failed to get all books", exception);
                    });
        }

        @Override
        public CompletableFuture<BookResponse> update (Long id, BookRequest request){
            log.info("Updating book with ID: {}", id);
            return CompletableFuture.supplyAsync(() -> {
                Book existingBook = bookRepository.findById(id)
                        .orElseThrow(() -> new ResourceNotFoundException("Book", "id", id));
                Author author = authorRepository.findById(request.getAuthorId())
                        .orElseThrow(() -> new ResourceNotFoundException("Author", "id", request.getAuthorId()));
                Book updatedBook = Mapper.toEntity(request, Book.class,"id");
                updatedBook.setId(existingBook.getId());
                updatedBook.setAuthor(author);
                bookRepository.save(updatedBook);
                return Mapper.toDto(updatedBook,BookResponse.class);
            }, executor);
        }

        @Override
        public CompletableFuture<Void> delete (Long id){
            log.info("Deleting book with ID: {}", id);
            return CompletableFuture.runAsync(() -> {
                Book book = bookRepository.findById(id)
                        .orElseThrow(() -> new ResourceNotFoundException("Book", "id", id));
                bookRepository.delete(book);
                log.info("Book deleted with ID: {}", id);
            }, executor).exceptionally(exception -> {
                log.error("Failed to delete book with ID {}: {}", id, exception.getMessage());
                throw new RuntimeException("Failed to delete book", exception);
            });
        }


        @Override
        public CompletableFuture<List<BookResponse>> getBooksByAuthorId (Long authorId){
            return CompletableFuture.supplyAsync(() -> {
                List<Book> books = bookRepository.findByAuthorId(authorId);
                if (books.isEmpty()) {
                    throw new ResourceNotFoundException("Author", "authorId", authorId);
                }
                return books.stream()
                        .map(book -> Mapper.toDto(book, BookResponse.class))
                        .collect(Collectors.toList());
            }, executor);
        }
    }
