package com.example.backend.dto.response;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;

import java.time.LocalDateTime;
import java.util.List;

@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
@AllArgsConstructor
@NoArgsConstructor
public class BookDataResponse {
    private String bookDataId;
    String name;
    String slug;
    String status;
    String thumbUrl;
    boolean subDocQuyen;
    List<String> categorySlug;
    String userId;
    LocalDateTime createdAt;
    LocalDateTime updatedAt;
}
