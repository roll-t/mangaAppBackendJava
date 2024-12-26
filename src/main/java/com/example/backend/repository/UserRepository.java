package com.example.backend.repository;

import com.example.backend.entity.Role;
import com.example.backend.entity.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;


@Repository
public interface UserRepository extends JpaRepository<User, String> {
    boolean existsByEmail(String email);

    List<User> findByRolesContaining(Role role);

    Optional<User> findByEmail(String email);

    @Query("SELECT u FROM User u WHERE u.creationTime BETWEEN :start AND :end")
    List<User> findUsersCreatedBetween(@Param("start") LocalDateTime start, @Param("end") LocalDateTime end);

    @Query("SELECT COUNT(u) FROM User u WHERE u.creationTime BETWEEN :start AND :end")
    int countUsersCreatedBetween(@Param("start") LocalDateTime start, @Param("end") LocalDateTime end);

    @Query("SELECT u FROM User u JOIN u.books b GROUP BY u.uid ORDER BY COUNT(b) DESC")
    Page<User> findTop10UsersWithMostBooks(Pageable pageable);

}
