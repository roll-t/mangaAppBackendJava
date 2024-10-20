package com.example.backend.repository;

import com.example.backend.entity.BookData;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface BookDataRepository extends JpaRepository<BookData, String> {
}
