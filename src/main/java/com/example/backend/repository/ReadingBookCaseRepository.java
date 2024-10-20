package com.example.backend.repository;

import com.example.backend.entity.BookData;
import com.example.backend.entity.ReadingBookCase;
import com.example.backend.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ReadingBookCaseRepository extends JpaRepository<ReadingBookCase, String> {
    // Tìm tất cả ReadingBookCase theo uid của user
    List<ReadingBookCase> findByUser_Uid(String uid);

    // Tìm ReadingBookCase theo user và bookData
    ReadingBookCase findByUserAndBookData(User user, BookData bookData);
}
