package com.vijay.book_managment.model;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserRoleRequest {
    private Long userRoleId;
    private Long userId;
    private Long roleId;

    // Getters and Setters
}
