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
public class ReadingBookCase {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(nullable = false, unique = true)
    String id;

    @ManyToOne
    @JoinColumn(name = "book_data_id", referencedColumnName = "bookDataId", nullable = false)
    BookData bookData;

    @ManyToOne
    @JoinColumn(name = "uid", referencedColumnName = "uid", nullable = false)
    User user;

    String chapterName;
    LocalDateTime readingDate;
    double positionReading;
}
