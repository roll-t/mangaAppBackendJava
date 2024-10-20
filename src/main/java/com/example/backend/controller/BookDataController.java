package com.example.backend.controller;


import com.example.backend.dto.request.ApiResponse;
import com.example.backend.dto.request.BookDataRequest;
import com.example.backend.dto.request.BookUpdateRequest;
import com.example.backend.dto.response.BookDataResponse;
import com.example.backend.service.BookDataService;
import jakarta.validation.Valid;
import lombok.AccessLevel;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.web.bind.annotation.*;

import java.awt.print.Book;
import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/book-data")
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@Data
@CrossOrigin(origins = "http://localhost:3000")
public class BookDataController {
    BookDataService bookDataService;

    @GetMapping
    public ApiResponse<List<BookDataResponse>> getAllBookData() {
        return ApiResponse.<List<BookDataResponse>>builder().result(bookDataService.getAll()).build();
    }

    @PostMapping("/{uid}")
    public ApiResponse<BookDataResponse> createBookData(@RequestBody @Valid BookDataRequest bookDataRequest, @PathVariable("uid") String uid) {
        return ApiResponse.<BookDataResponse>builder().result(bookDataService.create(bookDataRequest, uid)).build();
    }

    @GetMapping("/{bid}")
    public ApiResponse<BookDataResponse> getBookData(@PathVariable("bid") String bid) {
        return ApiResponse.<BookDataResponse>builder()
                .result(bookDataService.get(bid))
                .build();
    }

    @DeleteMapping("/{bid}")
    public ApiResponse<Boolean> deleteBookData(@PathVariable("bid") String bid) {
        try {
            bookDataService.delete(bid);
            return ApiResponse.<Boolean>builder()
                    .result(true)
                    .build();
        } catch (Exception e) {
            return ApiResponse.<Boolean>builder()
                    .result(false)
                    .build();
        }
    }

    @PutMapping("{bid}")
    public ApiResponse<Boolean> updateBookData(@PathVariable("bid") String bid, @RequestBody @Valid BookUpdateRequest bookUpdateRequest) {
        try {
            bookDataService.update(bid, bookUpdateRequest);
            return ApiResponse.<Boolean>builder().result(true).build();
        } catch (Exception e) {
            return ApiResponse.<Boolean>builder().result(false).build();
        }
    }

    @GetMapping("/category/{categoryId}")
    public ApiResponse<List<BookDataResponse>> getBooksByCategory(@PathVariable("categoryId") Integer categoryId) {
        List<BookDataResponse> books = bookDataService.findByCategory(categoryId);
        return ApiResponse.<List<BookDataResponse>>builder().result(books).build();
    }

    @GetMapping("/status/{statusName}")
    public ApiResponse<List<BookDataResponse>> getBooksByStatus(@PathVariable("statusName") String status) {
        List<BookDataResponse> books = bookDataService.findByStatus(status);
        return ApiResponse.<List<BookDataResponse>>builder().result(books).build();
    }


    @GetMapping("/date-range")
    public ApiResponse<List<BookDataResponse>> getBooksByDateRange(
            @RequestParam("startDate") String startDate,
            @RequestParam("endDate") String endDate) {

        LocalDateTime start = LocalDateTime.parse(startDate);
        LocalDateTime end = LocalDateTime.parse(endDate);

        List<BookDataResponse> books = bookDataService.findByCreationDateRange(start, end);
        return ApiResponse.<List<BookDataResponse>>builder().result(books).build();
    }

    @GetMapping("/user/{userId}")
    public ApiResponse<List<BookDataResponse>> getBooksByUser(@PathVariable("userId") String userId) {
        List<BookDataResponse> books = bookDataService.findByUser(userId);
        return ApiResponse.<List<BookDataResponse>>builder().result(books).build();
    }

    @GetMapping("/search/text")
    public ApiResponse<List<BookDataResponse>> searchBookByNameOrSlug(@RequestParam("text") String text) {
        List<BookDataResponse> books = bookDataService.searchByNameOrSlug(text);
        return ApiResponse.<List<BookDataResponse>>builder().result(books).build();
    }

    @GetMapping("/category/{categorySlug}")
    public ApiResponse<List<BookDataResponse>> getBooksByCategorySlug(@PathVariable("categorySlug") String categorySlug) {
        List<BookDataResponse> books = bookDataService.findByCategorySlug(categorySlug);
        return ApiResponse.<List<BookDataResponse>>builder().result(books).build();
    }

    @GetMapping("/names")
    public ApiResponse<List<BookDataResponse>> getBooksByNames(@RequestParam List<String> names) {
        List<BookDataResponse> books = bookDataService.findByNames(names);
        return ApiResponse.<List<BookDataResponse>>builder().result(books).build();
    }

    @GetMapping("/slugs")
    public ApiResponse<List<BookDataResponse>> getBooksBySlugs(@RequestParam List<String> slugs) {
        List<BookDataResponse> books = bookDataService.findBySlugs(slugs);
        System.out.print(books);
        return ApiResponse.<List<BookDataResponse>>builder().result(books).build();
    }


}