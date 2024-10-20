package com.example.backend.dto.request;

import com.example.backend.entity.Category;
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
public class BookUpdateRequest {
    String name;
    String slug;
    String thumbUrl;
    String status;
    List<Integer> categoryId;
    LocalDateTime updatedAt;
}
