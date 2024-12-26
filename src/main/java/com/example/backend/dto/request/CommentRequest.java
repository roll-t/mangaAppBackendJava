package com.example.backend.dto.request;

import lombok.Data;

@Data
public class CommentRequest {
    private String userId;
    private String content;
    private String chapterId;
}
