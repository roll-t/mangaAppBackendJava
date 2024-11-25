package com.example.backend.service;

import com.example.backend.dto.request.FavoriteRequest;
import com.example.backend.dto.response.FavoriteResponse;
import com.example.backend.entity.BookData;
import com.example.backend.entity.FavoriteBook;
import com.example.backend.entity.User;
import com.example.backend.mapper.FavoriteBookMapper;
import com.example.backend.repository.BookDataRepository;
import com.example.backend.repository.FavoriteBookRepository;
import com.example.backend.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class FavoriteBookService {

    private final FavoriteBookRepository favoriteBookRepository;
    private final BookDataRepository bookDataRepository;
    private final UserRepository userRepository;
    private final FavoriteBookMapper favoriteBookMapper;


    // Method to like a book
    public FavoriteResponse likeBook(FavoriteRequest favoriteRequest) {
        // Get the book data by ID
        BookData bookData = bookDataRepository.findById(favoriteRequest.getBookDataId())
                .orElseThrow(() -> new RuntimeException("Book not found"));

        // Create FavoriteBook entity
        FavoriteBook favoriteBook = FavoriteBook.builder()
                .user(User.builder().uid(favoriteRequest.getUserId()).build())
                .bookData(bookData)
                .favoriteDate(LocalDateTime.now())
                .build();

        // Save favorite
        FavoriteBook savedFavorite = favoriteBookRepository.save(favoriteBook);

        // Return response with full book data
        return favoriteBookMapper.toFavoriteBookResponse(savedFavorite);
    }

    // Method to get all books a user has liked
    public List<FavoriteResponse> getAllFavoriteBooksByUserId(String userId) {
        List<FavoriteBook> favoriteBooks = favoriteBookRepository.findByUserUid(userId);
        return favoriteBooks.stream()
                .map(favoriteBookMapper::toFavoriteBookResponse)
                .collect(Collectors.toList());
    }

    // Method to check if a user has liked a book
    public boolean isBookLiked(String bookDataId, String userId) {
        return favoriteBookRepository.existsByBookDataBookDataIdAndUserUid(bookDataId, userId);
    }


    // Method to unlike (remove) a book
    public void unlikeBook(String bookDataId, String userId) {
        FavoriteBook favoriteBook = favoriteBookRepository
                .findByBookDataBookDataIdAndUserUid(bookDataId, userId)
                .orElseThrow(() -> new RuntimeException("Favorite book not found for this user"));
        favoriteBookRepository.delete(favoriteBook);
    }
}
