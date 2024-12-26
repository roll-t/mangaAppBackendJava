package com.example.backend.entity;


import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.util.List;

@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level= AccessLevel.PRIVATE)
@Data
public class Comic {
    @Id
    String id;
    String title;
    String description;
    String thumb;
    String domain;
    List<Object> chapters;
    List<Object> category;
}
