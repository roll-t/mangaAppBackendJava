package com.example.backend.dto.response;


import com.example.backend.entity.BookData;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;

import java.util.List;

@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
@AllArgsConstructor
@NoArgsConstructor
public class BookModelResponse {
    String titlePage;
    String domainImage;
    List<BookData> bookData;

}
