package com.example.backend.service;

import com.example.backend.dto.request.ApiResponse;
import com.example.backend.dto.request.CategoryRequest;
import com.example.backend.dto.response.CategoryResponse;
import com.example.backend.entity.Category;
import com.example.backend.entity.User;
import com.example.backend.mapper.CategoryMapper;
import com.example.backend.repository.CategoryRepository;
import jakarta.transaction.Transactional;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class CategoryService {

    CategoryMapper categoryMapper;
    CategoryRepository categoryRepository;

    @Transactional
    public CategoryResponse create(CategoryRequest request) {
        Category category = categoryMapper.toCategory(request);
        categoryRepository.save(category);
        return categoryMapper.toCategoryResponse(category);
    }

    public List<CategoryResponse> getAll() {
        List<Category> categories = categoryRepository.findAll();
        if (categories.isEmpty()) {
            throw new RuntimeException("No users found");
        }
        return categoryMapper.toCategoryList(categories);
    }

    public void delete(int id) {
        categoryRepository.deleteById(id);
    }

    public CategoryResponse update(int id, CategoryRequest request) {
        Category category = categoryRepository.findById(id).orElseThrow(() -> new RuntimeException("Category not found")); // Updated error message
        categoryMapper.updateCategory(category, request);
        categoryRepository.save(category);
        return categoryMapper.toCategoryResponse(category);
    }
}
