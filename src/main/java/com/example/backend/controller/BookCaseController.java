package com.example.backend.controller;

import com.example.backend.dto.request.ApiResponse;
import com.example.backend.dto.request.FavoriteRequest;
import com.example.backend.dto.request.ReadingBookCaseRequest;
import com.example.backend.dto.response.FavoriteResponse;
import com.example.backend.dto.response.ReadingBookCaseResponse;
import com.example.backend.service.BookCaseService;
import com.example.backend.service.FavoriteBookService;
import lombok.AccessLevel;
import lombok.Data;
import lombok.experimental.FieldDefaults;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/book-case")
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@Data
@CrossOrigin(origins = "http://localhost:3000")
public class BookCaseController {
    BookCaseService bookCaseService;
    FavoriteBookService favoriteBookService;  // Inject the FavoriteBookService


    @PostMapping()
    public ApiResponse<ReadingBookCaseResponse> createReadingBookCase(@RequestBody ReadingBookCaseRequest readingBookCaseRequest) {
        return ApiResponse.<ReadingBookCaseResponse>builder()
                .result(bookCaseService.createOrUpdateReadingBookCase(readingBookCaseRequest))
                .build();
    }

    @GetMapping("/{uid}")
    public ApiResponse<List<ReadingBookCaseResponse>> getReadingBookCase(@PathVariable("uid") String uid) {
        return ApiResponse.<List<ReadingBookCaseResponse>>builder()
                .result(bookCaseService.getAllReadingBooksByUserId(uid))
                .build();
    }

    @DeleteMapping("/{id}")
    public ApiResponse<Void> deleteReadingBookCase(@PathVariable("id") String id) {
        bookCaseService.deleteReadingBookCase(id);
        return ApiResponse.<Void>builder()
                .result(null)
                .build();
    }

    // New endpoint to get all books that a user has liked
    @GetMapping("/favorites/{userId}")
    public ApiResponse<List<FavoriteResponse>> getAllFavoriteBooks(@PathVariable("userId") String userId) {
        List<FavoriteResponse> favoriteBooks = favoriteBookService.getAllFavoriteBooksByUserId(userId);
        return ApiResponse.<List<FavoriteResponse>>builder()
                .result(favoriteBooks)
                .build();
    }

    // New endpoint for liking a book
    @PostMapping("/favorite")
    public ApiResponse<FavoriteResponse> likeBook(@RequestBody FavoriteRequest favoriteBookRequest) {
        // Call the FavoriteBookService to like the book
        FavoriteResponse response = favoriteBookService.likeBook(favoriteBookRequest);
        return ApiResponse.<FavoriteResponse>builder()
                .result(response)
                .build();
    }

    // Endpoint to unlike a book
    @DeleteMapping("/favorite/{bookDataId}/{userId}")
    public ApiResponse<Void> unlikeBook(@PathVariable("bookDataId") String bookDataId,
                                        @PathVariable("userId") String userId) {
        favoriteBookService.unlikeBook(bookDataId, userId);
        return ApiResponse.<Void>builder()
                .result(null)
                .build();
    }

    // New endpoint to check if a user has liked a book
    @GetMapping("/favorite/{bookDataId}/{userId}")
    public ApiResponse<Boolean> isBookLiked(@PathVariable("bookDataId") String bookDataId,
                                            @PathVariable("userId") String userId) {
        boolean isLiked = favoriteBookService.isBookLiked(bookDataId, userId);
        return ApiResponse.<Boolean>builder()
                .result(isLiked)
                .build();
    }

}
