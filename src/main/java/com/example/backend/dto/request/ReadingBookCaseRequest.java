package com.example.backend.dto.request;

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
public class ReadingBookCaseRequest {
    String bookDataId; // ID của sách
    String uid; // ID của người dùng
    String chapterName; // Tên chương
    LocalDateTime readingDate; // Ngày đọc
    double positionReading; // Vị trí đọc
}
