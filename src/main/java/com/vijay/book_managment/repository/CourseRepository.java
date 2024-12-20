package com.vijay.book_managment.repository;

import com.vijay.book_managment.entity.Course;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CourseRepository extends JpaRepository<Course, Long> {
}

