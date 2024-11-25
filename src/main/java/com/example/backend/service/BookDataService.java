package com.example.backend.service;

import com.example.backend.dto.request.BookDataRequest;
import com.example.backend.dto.request.BookUpdateRequest;
import com.example.backend.dto.response.BookDataResponse;
import com.example.backend.entity.BookData;
import com.example.backend.entity.Category;
import com.example.backend.entity.User;
import com.example.backend.mapper.BookDataMapper;
import com.example.backend.repository.BookDataRepository;
import com.example.backend.repository.CategoryRepository;
import com.example.backend.repository.UserRepository;
import com.example.backend.utils.StringUtils;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
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

    public List<BookDataResponse> getAll(int page, int size) {
        Pageable pageable = PageRequest.of(page, size, Sort.by(Sort.Order.desc("createdAt")));
        // Fetch paginated result from the repository
        Page<BookData> bookDataPage = bookDataRepository.findAll(pageable);

        return bookDataPage.stream()
                .filter(bookData -> !bookData.getStatus().contains("CANCELLED"))
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

    public BookDataResponse addSliderToStatus(String bookId) {
        BookData bookData = bookDataRepository.findById(bookId)
                .orElseThrow(() -> new IllegalArgumentException("Book with ID " + bookId + " not found"));
        // Check if "SLIDER" is not already part of the status
        if (!bookData.getStatus().contains("SLIDER")) {
            bookData.setStatus(bookData.getStatus() + " | SLIDER");
            bookDataRepository.save(bookData); // Save the updated entity
        }

        return bookDataMapper.toBookDataResponse(bookData);
    }


    public void delete(String id) {
        bookDataRepository.deleteById(id);
    }

    public void update(String id, BookUpdateRequest bookUpdateRequest) {

        BookData bookData = bookDataRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("BookData not found"));

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

    public List<BookDataResponse> findByCategoryAndStatus(String categorySlug, String status, int page, int size) {
        PageRequest pageable = PageRequest.of(page, size);
        System.out.print(categorySlug);
        System.out.print(status);
//        System.out.print(categorySlug);
        Page<BookData> bookDataPage = bookDataRepository.findByCategorySlugAndStatus(categorySlug, status, pageable);

        // Convert BookData entities to BookDataResponse DTOs
        return bookDataPage.stream()
                .map(bookDataMapper::toBookDataResponse) // Use the mapper to convert each BookData
                .collect(Collectors.toList());
    }


    public List<BookDataResponse> findByStatus(String statuses) {
        return bookDataRepository.findAll().stream()
                .filter(bookData -> bookData.getStatus().contains(statuses))
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

    public BookDataResponse updateStatus(String bookId, String newStatus) {
        BookData bookData = bookDataRepository.findById(bookId).orElseThrow(() -> new IllegalArgumentException("Book with ID " + bookId + " not found"));
        if (!bookData.getStatus().contains(newStatus)) {
            bookData.setStatus(bookData.getStatus() + " | " + newStatus);
            bookData = bookDataRepository.save(bookData);
        }
        return bookDataMapper.toBookDataResponse(bookData);
    }

    public List<BookDataResponse> findByCreationDateRange(LocalDateTime startDate, LocalDateTime endDate) {
        return bookDataRepository.findAll().stream()
                .filter(bookData ->
                        bookData.getCreatedAt().isAfter(startDate) &&
                                bookData.getCreatedAt().isBefore(endDate))
                .map(bookDataMapper::toBookDataResponse)
                .collect(Collectors.toList());
    }

    public BookDataResponse removeStatusFromBook(String bookId, String statusToRemove) {
        BookData bookData = bookDataRepository.findById(bookId)
                .orElseThrow(() -> new IllegalArgumentException("Book with ID " + bookId + " not found"));
        if (bookData.getStatus().contains(statusToRemove)) {
            bookData.setStatus(bookData.getStatus().replace(" | " + statusToRemove, "").replace(statusToRemove + " | ", ""));
            bookData = bookDataRepository.save(bookData);
        }
        return bookDataMapper.toBookDataResponse(bookData);
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

    // Tính phần trăm tăng trưởng sách trong tuần
    public double calculateWeeklyGrowthPercentage() {
        LocalDateTime oneWeekAgo = LocalDateTime.now().minusWeeks(1);
        long booksLastWeek = bookDataRepository.countByCreatedAtAfter(oneWeekAgo);

        LocalDateTime twoWeeksAgo = LocalDateTime.now().minusWeeks(2);
        long booksTwoWeeksAgo = bookDataRepository.countByCreatedAtAfter(twoWeeksAgo);

        if (booksTwoWeeksAgo == 0) {
            return 0;  // Nếu không có sách được tạo trong 2 tuần trước, tránh chia cho 0
        }

        return ((double) booksLastWeek - booksTwoWeeksAgo) / booksTwoWeeksAgo * 100;
    }

    // Tính phần trăm tăng trưởng sách trong tháng
    public double calculateMonthlyGrowthPercentage() {
        LocalDateTime oneMonthAgo = LocalDateTime.now().minusMonths(1);
        long booksLastMonth = bookDataRepository.countByCreatedAtAfter(oneMonthAgo);

        LocalDateTime twoMonthsAgo = LocalDateTime.now().minusMonths(2);
        long booksTwoMonthsAgo = bookDataRepository.countByCreatedAtAfter(twoMonthsAgo);

        if (booksTwoMonthsAgo == 0) {
            return 0;  // Nếu không có sách được tạo trong 2 tháng trước, tránh chia cho 0
        }

        return ((double) booksLastMonth - booksTwoMonthsAgo) / booksTwoMonthsAgo * 100;
    }


    // Tính số sách được tạo trong tuần
    public long getBooksCreatedThisWeek() {
        LocalDateTime oneWeekAgo = LocalDateTime.now().minusWeeks(1);
        return bookDataRepository.countByCreatedAtAfter(oneWeekAgo);  // Đếm số sách được tạo trong tuần qua
    }

    // Tính tổng số sách
    public long getTotalBooksCount() {
        return bookDataRepository.count();  // Đếm tổng số sách trong cơ sở dữ liệu
    }

    // Tính phần trăm tăng trưởng sách trong tuần so với tổng số sách
    public double calculateWeeklyGrowthPercentageOnBase() {
        long totalBooks = getTotalBooksCount();  // Lấy tổng số sách
        if (totalBooks == 0) {
            return 0; // Tránh chia cho 0 nếu không có sách nào
        }

        long booksThisWeek = getBooksCreatedThisWeek();  // Lấy số sách tạo mới trong tuần

        // Tính phần trăm tăng trưởng sách trong tuần so với tổng số sách
        return ((double) booksThisWeek / totalBooks) * 100;
    }
}
