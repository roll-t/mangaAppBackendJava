package com.example.backend.dto.response;

import com.example.backend.entity.BookData;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;

import java.time.LocalDateTime;

@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
@AllArgsConstructor
@NoArgsConstructor
public class ReadingBookCaseResponse {
    long id;
    String bookDataId;
    String uid;
    String chapterName;
    LocalDateTime readingDate;
    double positionReading;
    // Thêm BookData
    BookData bookData;
}
