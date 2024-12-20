package com.vijay.book_managment.service;

import com.vijay.book_managment.model.BookRequest;
import com.vijay.book_managment.model.BookResponse;

import java.util.List;
import java.util.Set;
import java.util.concurrent.CompletableFuture;

public interface BookService extends iCrudService<BookRequest, BookResponse,Long>{

    public CompletableFuture<List<BookResponse>> getBooksByAuthorId(Long authorId);
}
