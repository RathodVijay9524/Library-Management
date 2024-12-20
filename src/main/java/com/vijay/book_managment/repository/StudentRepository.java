package com.vijay.book_managment.repository;

import com.vijay.book_managment.entity.Student;
import org.springframework.data.jpa.repository.JpaRepository;

public interface StudentRepository extends JpaRepository<Student, Long> {
}
