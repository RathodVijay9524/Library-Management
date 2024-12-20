package com.vijay.book_managment.model;



import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Set;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CourseRequest {
    private String name;
    private Set<Long> studentIds; // List of student IDs enrolled in the course
}
