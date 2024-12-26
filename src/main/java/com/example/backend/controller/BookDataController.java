package com.example.backend.controller;


import com.example.backend.dto.request.ApiResponse;
import com.example.backend.dto.request.BookDataRequest;
import com.example.backend.dto.request.BookUpdateRequest;
import com.example.backend.dto.response.BookDataResponse;
import com.example.backend.dto.response.TopAuthResponse;
import com.example.backend.service.BookDataService;
import jakarta.persistence.EntityNotFoundException;
import jakarta.validation.Valid;
import lombok.AccessLevel;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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
    public ApiResponse<List<BookDataResponse>> getAllBookData(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "50") int size) {

        List<BookDataResponse> bookDataList = bookDataService.getAll(page, size);

        return ApiResponse.<List<BookDataResponse>>builder()
                .result(bookDataList)
                .build();
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

    @PutMapping("/{bookId}/update-status")
    public ApiResponse<BookDataResponse> updateBookStatus(@PathVariable("bookId") String bookId,
                                                          @RequestParam("status") String status) {
        try {
            BookDataResponse updatedBook = bookDataService.updateStatus(bookId, status);
            return ApiResponse.<BookDataResponse>builder().result(updatedBook).build();
        } catch (Exception e) {
            return ApiResponse.<BookDataResponse>builder()
                    .build();
        }
    }

    @PutMapping("/{bookId}/remove-status")
    public ApiResponse<BookDataResponse> removeStatusFromBook(
            @PathVariable String bookId,
            @RequestParam String statusToRemove) {

        BookDataResponse updatedBookData = bookDataService.removeStatusFromBook(bookId, statusToRemove);

        return ApiResponse.<BookDataResponse>builder()
                .result(updatedBookData)
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
            return ApiResponse.<Boolean>builder().result(true).message("Book updated successfully").build();
        } catch (EntityNotFoundException e) {
            return ApiResponse.<Boolean>builder().result(false).message("Book not found").build();
        } catch (Exception e) {
            return ApiResponse.<Boolean>builder().result(false).message("Failed to update book: " + e.getMessage()).build();
        }
    }

    @GetMapping("/status/{statusName}")
    public ApiResponse<List<BookDataResponse>> getBooksByStatus(@PathVariable("statusName") String status) {
        List<BookDataResponse> books = bookDataService.findByStatus(status);
        return ApiResponse.<List<BookDataResponse>>builder().result(books).build();
    }

    @PatchMapping("/{bookId}/add-slider")
    public ApiResponse<BookDataResponse> addSliderToStatus(@PathVariable("bookId") String bookId) {
        BookDataResponse updatedBook = bookDataService.addSliderToStatus(bookId);
        return ApiResponse.<BookDataResponse>builder().result(updatedBook).build();
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

    @GetMapping("/search/{text}")
    public ApiResponse<List<BookDataResponse>> searchBookByNameOrSlug(@PathVariable("text") String text) {
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

    @GetMapping("/category/{categorySlug}/status/{statusName}")
    public ApiResponse<List<BookDataResponse>> getBooksByCategoryAndStatus(
            @PathVariable("categorySlug") String categorySlug,
            @PathVariable("statusName") String status,
            @RequestParam(value = "page", defaultValue = "0") int page,
            @RequestParam(value = "size", defaultValue = "10") int size) {
        List<BookDataResponse> books = bookDataService.findByCategoryAndStatus(categorySlug, status, page, size);
        return ApiResponse.<List<BookDataResponse>>builder().result(books).build();
    }

    @GetMapping("/slugs")
    public ApiResponse<List<BookDataResponse>> getBooksBySlugs(@RequestParam List<String> slugs) {
        List<BookDataResponse> books = bookDataService.findBySlugs(slugs);
        return ApiResponse.<List<BookDataResponse>>builder().result(books).build();
    }

    // Thống kê số sách được tạo trong tuần
    @GetMapping("/statistics/weekly-growth")
    public ApiResponse<Double> getWeeklyGrowthPercentage() {
        try {
            double growthPercentage = bookDataService.calculateWeeklyGrowthPercentage();
            return ApiResponse.<Double>builder()
                    .result(growthPercentage)
                    .build();
        } catch (Exception e) {
            return ApiResponse.<Double>builder()
                    .code(1)
                    .message("Error calculating weekly growth percentage")
                    .build();
        }
    }

    // Thống kê số sách được tạo trong tháng
    @GetMapping("/statistics/monthly-growth")
    public ApiResponse<Double> getMonthlyGrowthPercentage() {
        try {
            double growthPercentage = bookDataService.calculateMonthlyGrowthPercentage();
            return ApiResponse.<Double>builder()
                    .result(growthPercentage)
                    .build();
        } catch (Exception e) {
            return ApiResponse.<Double>builder()
                    .code(1)
                    .message("Error calculating monthly growth percentage")
                    .build();
        }
    }

    // Thống kê phần trăm sách được tạo trong tuần so với tổng số sách
    @GetMapping("/statistics/weekly-growth-percentage")
    public ApiResponse<Double> getWeeklyGrowthPercentageOnBase() {
        try {
            double growthPercentage = bookDataService.calculateWeeklyGrowthPercentageOnBase();
            return ApiResponse.<Double>builder()
                    .result(growthPercentage)
                    .build();
        } catch (Exception e) {
            return ApiResponse.<Double>builder()
                    .code(1)
                    .message("Error calculating weekly growth percentage")
                    .build();
        }
    }


    @GetMapping("/custom-week")
    public ApiResponse<List<BookDataResponse>> getBooksCreatedInCustomWeek(
            @RequestParam("startDate") String startDate,
            @RequestParam("endDate") String endDate) {

        LocalDateTime start = LocalDateTime.parse(startDate);
        LocalDateTime end = LocalDateTime.parse(endDate);

        List<BookDataResponse> books = bookDataService.getBooksCreatedInCustomWeek(start, end);
        return ApiResponse.<List<BookDataResponse>>builder()
                .result(books)
                .build();
    }

    @GetMapping("/top-author")
    public ApiResponse<List<TopAuthResponse>> getTopAuthors() {
        List<TopAuthResponse> topAuthors = bookDataService.getTopAuthorsWithMostBooks();
        return ApiResponse.<List<TopAuthResponse>>builder()
                .result(topAuthors)
                .build();
    }

}