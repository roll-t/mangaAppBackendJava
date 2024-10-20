package com.example.backend.dto.request;


import lombok.*;
import lombok.experimental.FieldDefaults;


@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
@AllArgsConstructor
@NoArgsConstructor
public class CategoryRequest {
    int id;
    String name;
    String slug;
}
