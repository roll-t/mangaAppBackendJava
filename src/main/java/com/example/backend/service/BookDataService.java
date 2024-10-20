package com.example.backend.service;

import com.example.backend.dto.request.BookDataRequest;
import com.example.backend.dto.request.BookUpdateRequest;
import com.example.backend.dto.response.BookDataResponse;
import com.example.backend.entity.BookData;
import com.example.backend.entity.Category;
import com.example.backend.entity.Chapter;
import com.example.backend.entity.User;
import com.example.backend.mapper.BookDataMapper;
import com.example.backend.repository.BookDataRepository;
import com.example.backend.repository.CategoryRepository;
import com.example.backend.repository.UserRepository;
import com.example.backend.utils.StringUtils;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class BookDataService {
    BookDataMapper bookDataMapper;
    BookDataRepository bookDataRepository;
    CategoryRepository categoryRepository;
    UserRepository userRepository;

    public List<BookDataResponse> getAll() {
        return bookDataRepository.findAll().stream()
                .filter(bookData -> !"CANCELLED".equalsIgnoreCase(bookData.getStatus()))
                .sorted(Comparator.comparing(BookData::getCreatedAt).reversed())
                .map(bookDataMapper::toBookDataResponse)
                .collect(Collectors.toList());
    }


    public BookDataResponse create(BookDataRequest bookDataRequest, String userId) {
        User user = userRepository.findById(userId).orElseThrow(() ->
                new IllegalArgumentException("User with id " + userId + " not found")
        );
        BookData bookData = bookDataMapper.toBookData(bookDataRequest);

        bookData.setUser(user);

        List<Category> categories = categoryRepository.findAllById(bookDataRequest.getCategoryId());
        bookData.setCategory(categories);
        bookData = bookDataRepository.save(bookData);
        return bookDataMapper.toBookDataResponse(bookData);
    }

    public void delete(String id) {
        bookDataRepository.deleteById(id);
    }

    public void update(String id, BookUpdateRequest bookUpdateRequest) {
        BookData bookData = bookDataRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("BookData not found"));
        System.out.println(bookData);

        bookDataMapper.updateBookDataFromRequest(bookData, bookUpdateRequest);

        if (bookUpdateRequest.getCategoryId() != null && !bookUpdateRequest.getCategoryId().isEmpty()) {
            List<Integer> categoryIds = bookUpdateRequest.getCategoryId();
            List<Category> categories = categoryRepository.findAllById(categoryIds);
            bookData.setCategory(categories);
        }
        bookData.setUpdatedAt(LocalDateTime.now());

        bookDataRepository.save(bookData);
    }

    public List<BookDataResponse> findByName(String name) {
        String normalizedSearchName = StringUtils.removeAccents(name).toLowerCase();

        return bookDataRepository.findAll().stream()
                .filter(bookData -> StringUtils.removeAccents(bookData.getName()).toLowerCase().contains(normalizedSearchName))
                .map(bookDataMapper::toBookDataResponse)
                .collect(Collectors.toList());
    }


    public BookDataResponse get(String id) {
        return bookDataMapper.toBookDataResponse(bookDataRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("BookData not found")));
    }

    public List<BookDataResponse> findByStatus(String status) {
        return bookDataRepository.findAll().stream()
                .filter(bookData -> status.equalsIgnoreCase(bookData.getStatus()))
                .map(bookDataMapper::toBookDataResponse)
                .collect(Collectors.toList());
    }

    public List<BookDataResponse> findByCategory(Integer categoryId) {
        Category category = categoryRepository.findById(categoryId)
                .orElseThrow(() -> new IllegalArgumentException("Category with id " + categoryId + " not found"));
        return bookDataRepository.findAll().stream()
                .filter(bookData -> bookData.getCategory().contains(category))
                .map(bookDataMapper::toBookDataResponse)
                .collect(Collectors.toList());
    }

    public List<BookDataResponse> findByCreationDateRange(LocalDateTime startDate, LocalDateTime endDate) {
        return bookDataRepository.findAll().stream()
                .filter(bookData ->
                        bookData.getCreatedAt().isAfter(startDate) &&
                                bookData.getCreatedAt().isBefore(endDate))
                .map(bookDataMapper::toBookDataResponse)
                .collect(Collectors.toList());
    }

    public List<BookDataResponse> findByUser(String userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("User with id " + userId + " not found"));

        return bookDataRepository.findAll().stream()
                .filter(bookData -> bookData.getUser().equals(user))
                .map(bookDataMapper::toBookDataResponse)
                .collect(Collectors.toList());
    }

    public List<BookDataResponse> searchByNameOrSlug(String searchText) {
        String normalizedSearchText = StringUtils.removeAccents(searchText).toLowerCase();

        return bookDataRepository.findAll().stream()
                .filter(bookData ->
                        StringUtils.removeAccents(bookData.getName()).toLowerCase().contains(normalizedSearchText) ||
                                StringUtils.removeAccents(bookData.getSlug()).toLowerCase().contains(normalizedSearchText))
                .map(bookDataMapper::toBookDataResponse)
                .collect(Collectors.toList());
    }

    public List<BookDataResponse> findByCategorySlug(String categorySlug) {
        return bookDataRepository.findAll().stream()
                .filter(bookData -> bookData.getCategory().stream()
                        .anyMatch(category -> category.getSlug().equalsIgnoreCase(categorySlug)))
                .map(bookDataMapper::toBookDataResponse)
                .collect(Collectors.toList());
    }


    public List<BookDataResponse> findByNames(List<String> names) {
        List<String> normalizedNames = names.stream()
                .map(name -> StringUtils.removeAccents(name).toLowerCase())
                .toList();

        return bookDataRepository.findAll().stream()
                .filter(bookData -> normalizedNames.contains(StringUtils.removeAccents(bookData.getName()).toLowerCase()))
                .map(bookDataMapper::toBookDataResponse)
                .collect(Collectors.toList());
    }

    public List<BookDataResponse> findBySlugs(List<String> slugs) {
        return bookDataRepository.findAll().stream()
                .filter(bookData -> slugs.contains(bookData.getSlug()))
                .map(bookDataMapper::toBookDataResponse)
                .collect(Collectors.toList());
    }


}
