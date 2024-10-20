package com.example.backend.dto.request;

import com.example.backend.entity.Category;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;

import java.util.List;

@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
@AllArgsConstructor
@NoArgsConstructor
public class BookDataRequest {
    String name;
    String slug;
    String status;
    String thumbUrl;
    List<Integer> categoryId;
    String userId;
}
