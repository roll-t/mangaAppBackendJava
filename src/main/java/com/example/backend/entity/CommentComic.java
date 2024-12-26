package com.example.backend.entity;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.time.LocalDateTime;

@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
public class CommentComic {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    String commentId;

    @Column(name = "comic_id", nullable = false)
    String comicId;

    @ManyToOne
    @JoinColumn(name = "user_id", referencedColumnName = "uid", nullable = false)
    User user; // The user who created the comment

    String content; // The content of the comment

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
