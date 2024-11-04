package com.example.backend.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
public class CommentResponse {
    private String commentId;
    private UserResponse user;
    private String content;
    private LocalDateTime createdAt;
}
