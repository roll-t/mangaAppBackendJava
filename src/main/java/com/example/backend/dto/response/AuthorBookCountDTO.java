package com.example.backend.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class AuthorBookCountDTO {
    private String userId;
    private Long bookCount;
}
