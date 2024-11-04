package com.example.backend.entity;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@Data

public class BookData {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    String bookDataId;
    String name;
    String slug;
    String status;
    String thumbUrl;
    boolean subDocQuyen;

    // tạo quan hệ với bản comment
    @OneToMany(mappedBy = "bookData", cascade = CascadeType.ALL, orphanRemoval = true)
    List<Comment> comments;

    // Many-to-Many relationship with Category
    @ManyToMany
    @JoinTable(
            name = "book_category",
            joinColumns = @JoinColumn(name = "book_data_id"),
            inverseJoinColumns = @JoinColumn(name = "id")
    )
    List<Category> category;

    LocalDateTime createdAt;

    LocalDateTime updatedAt;

    @PrePersist
    protected void onCreate() {
        createdAt = LocalDateTime.now();
        updatedAt = LocalDateTime.now();
    }

    // Many-to-One relationship with User (creator of the book)
    @ManyToOne
    @JoinColumn(name = "user_id") // This column will store the reference to the user
            User user;

    @PreUpdate
    protected void onUpdate() {
        updatedAt = LocalDateTime.now();
    }
}
