package com.vijay.book_managment.model;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserRoleResponse {
    private Long userRoleId;
    private Long userId;
    private Long roleId;

    // Getters and Setters
}


