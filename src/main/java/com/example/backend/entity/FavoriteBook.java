package com.example.backend.entity;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@Builder
public class  FavoriteBook {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(nullable = false, unique = true)
    String id;

    @ManyToOne
    @JoinColumn(name = "book_data_id", referencedColumnName = "bookDataId", nullable = false)
    BookData bookData;  // The book being liked

    @ManyToOne
    @JoinColumn(name = "uid", referencedColumnName = "uid", nullable = false)
    User user;  // The user who liked the book

    LocalDateTime favoriteDate;  // Timestamp for when the book was liked
}
