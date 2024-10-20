package com.example.backend.controller;

import com.example.backend.dto.request.ApiResponse;
import com.example.backend.dto.request.ReadingBookCaseRequest;
import com.example.backend.dto.response.ReadingBookCaseResponse;
import com.example.backend.service.BookCaseService;
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

}
