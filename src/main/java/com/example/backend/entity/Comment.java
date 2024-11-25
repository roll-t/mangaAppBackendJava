package com.example.backend.entity;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Comment {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    String commentId;

    @ManyToOne
    @JoinColumn(name = "book_data_id", referencedColumnName = "bookDataId", nullable = false) // Đảm bảo tên cột chính xác
    BookData bookData;


    @ManyToOne
    @JoinColumn(name = "chapter_id", referencedColumnName = "chapterId", nullable = true) // Nullable relationship to Chapter
    Chapter chapter;

    @ManyToOne
    @JoinColumn(name = "user_id",referencedColumnName = "uid", nullable = false)
    User user;

    String content;

    LocalDateTime createdAt;
    LocalDateTime updatedAt;

    @PrePersist
    protected void onCreate() {
        createdAt = LocalDateTime.now();
        updatedAt = LocalDateTime.now();
    }

    @PreUpdate
    protected void onUpdate() {
        updatedAt = LocalDateTime.now();
    }
}
