package com.vijay.book_managment.repository;

import com.vijay.book_managment.entity.Author;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AuthorRepository extends JpaRepository<Author, Long> {

}

