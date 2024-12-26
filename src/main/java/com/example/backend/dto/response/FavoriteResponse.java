package com.example.backend.dto.response;

import java.time.LocalDateTime;

import com.example.backend.entity.BookData;
import lombok.Data;

@Data
public class FavoriteResponse {
    private String id;            // The ID of the favorite book record
    private BookData bookData; // The ID of the liked book
    private String userId;        // The ID of the user who liked the book
    private LocalDateTime favoriteDate; // The date the book was liked
}
