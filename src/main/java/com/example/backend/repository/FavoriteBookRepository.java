package com.example.backend.repository;

import com.example.backend.entity.FavoriteBook;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface FavoriteBookRepository extends JpaRepository<FavoriteBook, String> {
    List<FavoriteBook> findByUserUid(String userId);
    Optional<FavoriteBook> findByBookDataBookDataIdAndUserUid(String bookDataId, String userId);
    boolean existsByBookData_BookDataIdAndUser_Uid(String bookDataId, String userId);
    boolean existsByBookDataBookDataIdAndUserUid(String bookDataId, String userId);
}
