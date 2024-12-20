package com.vijay.book_managment.model;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Set;
@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserResponse {
    private Long id;
    private String username;
    private String firstName;
    private String lastName;
    private String email;
    private String phone;
    private boolean enabled;
    private String profile;
    private Set<UserRoleResponse> userRoles;

    // Getters and Setters
}

