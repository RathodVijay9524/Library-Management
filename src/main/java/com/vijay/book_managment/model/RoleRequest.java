package com.vijay.book_managment.model;



import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Set;
@Data
@AllArgsConstructor
@NoArgsConstructor
public class RoleRequest {
    private Long roleId;
    private String roleName;
    private Set<UserRoleRequest> userRoles;

    // Getters and Setters
}
