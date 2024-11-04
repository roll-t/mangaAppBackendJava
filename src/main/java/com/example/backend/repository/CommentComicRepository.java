package com.example.backend.repository;

import com.example.backend.entity.CommentComic;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CommentComicRepository extends JpaRepository<CommentComic, String> {

    Page<CommentComic> findByComicId(String comicId, Pageable pageable);
}
