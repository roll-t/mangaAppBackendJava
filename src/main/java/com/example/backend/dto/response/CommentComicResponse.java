package com.example.backend.dto.response;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class CommentComicResponse {
    private String commentId;
    private String comicId;
    private String userId;
    private String content;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}