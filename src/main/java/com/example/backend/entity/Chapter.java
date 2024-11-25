package com.example.backend.entity;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@Data

public class Chapter {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    String chapterId;
    String chapterName;
    String chapterTitle;
    @Lob
    @Column(columnDefinition = "TEXT")
    String chapterContent;

    LocalDateTime createAt;

    @ManyToOne
    @JoinColumn(name = "book_data_id", referencedColumnName = "bookDataId", nullable = false)
    BookData bookData;
}
