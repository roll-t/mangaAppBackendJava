package com.example.backend.repository;

import com.example.backend.entity.BookData;
import com.example.backend.entity.BookModel;
import org.springframework.data.jpa.repository.JpaRepository;


public interface BookModelRepository extends JpaRepository<BookModel, String> {
}
