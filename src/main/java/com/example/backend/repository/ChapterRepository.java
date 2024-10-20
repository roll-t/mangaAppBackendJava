package com.example.backend.repository;

import com.example.backend.entity.Chapter;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ChapterRepository extends JpaRepository<Chapter, String> {
    List<Chapter> findByBookDataSlug(String slug);
}
