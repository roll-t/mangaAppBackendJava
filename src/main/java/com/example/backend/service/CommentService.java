package com.example.backend.service;

import com.example.backend.dto.request.CommentRequest;
import com.example.backend.dto.response.CommentResponse;
import com.example.backend.dto.response.UserResponse;
import com.example.backend.entity.BookData;
import com.example.backend.entity.Chapter;
import com.example.backend.entity.Comment;
import com.example.backend.entity.User;
import com.example.backend.mapper.CommentMapper;
import com.example.backend.mapper.UserMapper;
import com.example.backend.repository.BookDataRepository;
import com.example.backend.repository.ChapterRepository;
import com.example.backend.repository.CommentRepository;
import com.example.backend.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;


import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CommentService {

    private final CommentRepository commentRepository;
    private final BookDataRepository bookDataRepository;
    private final UserRepository userRepository;
    private final ChapterRepository chapterRepository;
    private final CommentMapper commentMapper;


    public CommentResponse createComment(String bookDataId, CommentRequest commentRequest) {
        BookData bookData = bookDataRepository.findById(bookDataId)
                .orElseThrow(() -> new RuntimeException("BookData not found"));
        User user = userRepository.findById(commentRequest.getUserId())
                .orElseThrow(() -> new RuntimeException("User not found"));

        Chapter chapter = null;
        if (commentRequest.getChapterId() != null) {
            chapter = chapterRepository.findById(commentRequest.getChapterId())
                    .orElseThrow(() -> new RuntimeException("Chapter not found"));
        }

        Comment comment = commentMapper.toEntity(commentRequest);
        comment.setBookData(bookData);
        comment.setUser(user);
        comment.setChapter(chapter);  // Set the chapter if it's present
        comment.setCreatedAt(LocalDateTime.now());

        Comment savedComment = commentRepository.save(comment);
        return commentMapper.toResponse(savedComment);
    }

    public List<CommentResponse> getCommentsByBookId(String bookDataId, int page) {
        Pageable pageable = PageRequest.of(page, 30); // Page size is 30
        Page<Comment> commentsPage = commentRepository.findByBookDataBookDataIdOrderByCreatedAtDesc(bookDataId, pageable);

        return commentsPage.getContent().stream()
                .map(commentMapper::toResponse)
                .collect(Collectors.toList());
    }


    public List<CommentResponse> getCommentsByChapterId(String chapterId, int page) {
        Pageable pageable = PageRequest.of(page, 30); // Page size is 30
        Page<Comment> commentsPage = commentRepository.findByChapterChapterIdOrderByCreatedAtDesc(chapterId, pageable);

        return commentsPage.getContent().stream()
                .map(commentMapper::toResponse)
                .collect(Collectors.toList());
    }

    public boolean updateComment(String commentId, CommentRequest commentRequest) {
        Comment comment = commentRepository.findById(commentId)
                .orElseThrow(() -> new RuntimeException("Comment not found"));

        comment.setContent(commentRequest.getContent());
        commentRepository.save(comment);

        return true;
    }

    public boolean deleteComment(String commentId) {
        Comment comment = commentRepository.findById(commentId)
                .orElseThrow(() -> new RuntimeException("Comment not found"));

        commentRepository.delete(comment);

        return true;
    }
}
