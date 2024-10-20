package com.example.backend.entity;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@Entity
@Builder
public class ReadingBookCase {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    @ManyToOne // Mối quan hệ với BookData
    @JoinColumn(name = "book_data_id", nullable = false)
    BookData bookData;

    @ManyToOne
    @JoinColumn(name = "uid", nullable = false)
    User user;

    String chapterName; // Tên chương
    LocalDateTime readingDate; // Ngày đọc
    double positionReading; // Vị trí đọc
}
