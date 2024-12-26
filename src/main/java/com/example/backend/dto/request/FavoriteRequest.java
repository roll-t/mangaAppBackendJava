package com.example.backend.dto.request;

import lombok.Data;
@Data
public class FavoriteRequest {
    private String bookDataId;  // The ID of the book being liked
    private String userId;      // The ID of the user liking the book
}
