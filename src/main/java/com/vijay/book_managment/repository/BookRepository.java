package com.vijay.book_managment.repository;

import com.vijay.book_managment.entity.Book;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface BookRepository extends JpaRepository<Book, Long> {

    // Custom query method to find books by authorId
    List<Book> findByAuthorId(Long authorId);
}

