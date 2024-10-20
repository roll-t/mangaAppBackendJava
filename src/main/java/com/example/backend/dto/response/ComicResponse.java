package com.example.backend.dto.response;


import lombok.*;
import lombok.experimental.FieldDefaults;

import java.util.List;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level= AccessLevel.PRIVATE)
@Data
public class ComicResponse {
    String title;
    String description;
    String thumb;
    String domain;
    List<Object> chapters;
    List<Object> category;

}
