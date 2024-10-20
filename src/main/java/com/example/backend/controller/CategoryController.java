package com.example.backend.controller;

import com.example.backend.dto.request.ApiResponse;
import com.example.backend.dto.request.CategoryRequest;
import com.example.backend.dto.response.CategoryResponse;
import com.example.backend.service.CategoryService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/category")
@RequiredArgsConstructor
@CrossOrigin(origins = "http://localhost:3000")
public class CategoryController {
    private final CategoryService categoryService;

    @PostMapping()
    ApiResponse<CategoryResponse> createCategory(@RequestBody @Valid CategoryRequest categoryRequest) {
        return ApiResponse.<CategoryResponse>builder()
                .result(categoryService.create(categoryRequest))
                .build();
    }

    @GetMapping()
    ApiResponse<List<CategoryResponse>> getCategories() {
        return ApiResponse.<List<CategoryResponse>>builder().result(categoryService.getAll()).build();
    }

    @DeleteMapping("/{id}")
    ApiResponse<Boolean> deleteCategory(@PathVariable("id") int id) {
        try {
            categoryService.delete(id);
            return ApiResponse.<Boolean>builder().result(true).build();
        } catch (Exception e) {
            return ApiResponse.<Boolean>builder().result(false).build();
        }
    }

    @PutMapping("/{id}")
    public ApiResponse<Boolean> updateCategory(
            @RequestBody @Valid CategoryRequest categoryRequest,
            @PathVariable("id") int id) {
        try {
            categoryService.update(id, categoryRequest);
            return ApiResponse.<Boolean>builder()
                    .result(true)
                    .build();
        } catch (RuntimeException e) {
            return ApiResponse.<Boolean>builder()
                    .result(false)
                    .build();
        }
    }

}
