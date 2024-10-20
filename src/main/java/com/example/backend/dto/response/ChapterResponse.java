package com.example.backend.dto.response;


import com.example.backend.entity.BookData;
import jakarta.persistence.Lob;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.w3c.dom.Text;

import java.time.LocalDate;

@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
@AllArgsConstructor
@NoArgsConstructor
public class ChapterResponse {
    String chapterId;
    String chapterName;
    String chapterTitle;
    String chapterContent;
    LocalDate createAt;
    String bookDataId;
}
