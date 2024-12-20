package com.vijay.book_managment;

import com.vijay.book_managment.entity.Author;
import com.vijay.book_managment.entity.Book;
import com.vijay.book_managment.repository.AuthorRepository;
import com.vijay.book_managment.repository.BookRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.HashSet;
import java.util.Set;

@SpringBootApplication
public class BookManagmentApplication implements CommandLineRunner {

    @Autowired
    private AuthorRepository authorRepository;
    @Autowired
    private BookRepository bookRepository;

    public static void main(String[] args) {
        SpringApplication.run(BookManagmentApplication.class, args);
    }

    @Override
    public void run(String... args) throws Exception {




    }
}
