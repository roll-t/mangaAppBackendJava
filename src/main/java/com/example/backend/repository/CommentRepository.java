package com.example.backend.repository;

import com.example.backend.entity.Comment;
import org.springframework.data.domain.Page;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface CommentRepository extends JpaRepository<Comment, String> {
    Page<Comment> findByBookDataBookDataIdOrderByCreatedAtDesc(String bookDataId, Pageable pageable);//Lấy danh sách comment theo cuốn sách
}
