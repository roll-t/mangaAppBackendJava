package com.example.backend.dto.request;

import lombok.Data;

@Data
public class CommentComicRequest {
    private String comicId; // ID of the comic
    private String userId;  // ID of the user creating the comment
    private String content;  // Content of the comment
}
