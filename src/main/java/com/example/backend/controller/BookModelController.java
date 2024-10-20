package com.example.backend.controller;


import com.example.backend.dto.request.ApiResponse;
import com.example.backend.dto.request.BookModelRequest;
import com.example.backend.dto.response.BookModelResponse;
import com.example.backend.service.BookService;
import jakarta.validation.Valid;
import lombok.AccessLevel;
import lombok.Data;
import lombok.experimental.FieldDefaults;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/book-model")
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@Data
public class BookModelController {
    BookService bookService;

    @PostMapping
    public ApiResponse<BookModelResponse> createBookModel(@RequestBody @Valid BookModelRequest bookModelRequest) {
        BookModelResponse bookModelResponse= bookService.create(bookModelRequest);
        return ApiResponse.<BookModelResponse>builder()
                .result(bookModelResponse)
                .build();
    }

}
