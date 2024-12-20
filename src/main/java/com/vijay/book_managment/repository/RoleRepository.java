package com.vijay.book_managment.repository;


import com.vijay.book_managment.entity.Role;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RoleRepository extends JpaRepository<Role,Long> {
}
