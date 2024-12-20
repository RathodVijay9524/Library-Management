package com.vijay.book_managment.service.impl;

import com.vijay.book_managment.entity.Author;
import com.vijay.book_managment.exception.ResourceNotFoundException;
import com.vijay.book_managment.util.Mapper;
import com.vijay.book_managment.model.AuthorRequest;
import com.vijay.book_managment.model.AuthorResponse;
import com.vijay.book_managment.repository.AuthorRepository;
import com.vijay.book_managment.service.AuthorService;
import lombok.AllArgsConstructor;
import lombok.extern.java.Log;
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
public class AuthorServiceImpl implements AuthorService {
    private final AuthorRepository authorRepository;
    private final Mapper mapper;
    private final Executor executor;

    @Override
    public CompletableFuture<AuthorResponse> create(AuthorRequest request) {
        return CompletableFuture.supplyAsync(() -> {
            Author entity = mapper.toEntity(request, Author.class, "id");
            authorRepository.save(entity);
            return mapper.toDto(entity, AuthorResponse.class);
        }, executor).exceptionally(exception -> {
            log.error("Failed to create author: {}", exception.getMessage());
            throw new RuntimeException("Failed to create author", exception);
        });
    }

    @Override
    public CompletableFuture<AuthorResponse> getById(Long id) {
        return CompletableFuture.supplyAsync(() -> {
            Author author = authorRepository.findById(id)
                    .orElseThrow(() -> new ResourceNotFoundException("Author", "id", id));
            return mapper.toDto(author, AuthorResponse.class);
        }, executor).exceptionally(exception -> {
            log.error("Failed to get author with ID {}: {}", id, exception.getMessage());
            throw new RuntimeException("Failed to get author", exception);
        });
    }

    @Override
    public CompletableFuture<Set<AuthorResponse>> getAll() {
        return CompletableFuture.supplyAsync(() -> {
            return authorRepository.findAll()
                    .stream()
                    .map(author -> mapper.toDto(author, AuthorResponse.class))
                    .collect(Collectors.toSet());
        }, executor).exceptionally(exception -> {
            log.error("Failed to fetch all authors: {}", exception.getMessage());
            throw new RuntimeException("Failed to fetch all authors", exception);
        });
    }

    @Override
    public CompletableFuture<AuthorResponse> update(Long id, AuthorRequest request) {
        return CompletableFuture.supplyAsync(() -> {
            Author existingAuthor = authorRepository.findById(id)
                    .orElseThrow(() -> new ResourceNotFoundException("Author", "id", id));

            Author updatedAuth = mapper.toEntity(request, Author.class);
            authorRepository.save(updatedAuth);
            return mapper.toDto(updatedAuth, AuthorResponse.class);
        }, executor).exceptionally(exception -> {
            log.error("Failed to update author with ID {}: {}", id, exception.getMessage());
            throw new RuntimeException("Failed to update author", exception);
        });
    }

    @Override
    public CompletableFuture<Void> delete(Long id) {
        return CompletableFuture.runAsync(() -> {
            Author author = authorRepository.findById(id)
                    .orElseThrow(() -> new ResourceNotFoundException("Author", "id", id));
            authorRepository.delete(author);
            log.info("Author with ID {} successfully deleted", id);
        }, executor).exceptionally(exception -> {
            log.error("Failed to delete author with ID {}: {}", id, exception.getMessage());
            throw new RuntimeException("Failed to delete author", exception);
        });
    }
}


