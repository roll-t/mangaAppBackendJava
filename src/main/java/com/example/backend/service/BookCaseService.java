package com.example.backend.service;

import com.example.backend.dto.request.ReadingBookCaseRequest;
import com.example.backend.dto.response.ReadingBookCaseResponse;
import com.example.backend.entity.BookData;
import com.example.backend.entity.ReadingBookCase;
import com.example.backend.entity.User;
import com.example.backend.mapper.ReadingBookCaseMapper;
import com.example.backend.repository.BookDataRepository;
import com.example.backend.repository.ReadingBookCaseRepository;
import com.example.backend.repository.UserRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class BookCaseService {

    ReadingBookCaseRepository readingBookCaseRepository;
    ReadingBookCaseMapper readingBookCaseMapper;
    UserRepository userRepository;
    BookDataRepository bookDataRepository;

    @Transactional
    public ReadingBookCaseResponse createOrUpdateReadingBookCase(ReadingBookCaseRequest request) {
        User user = userRepository.findById(request.getUid())
                .orElseThrow(() -> new RuntimeException("User not found"));

        BookData bookData = bookDataRepository.findById(request.getBookDataId())
                .orElseThrow(() -> new RuntimeException("Book not found"));

        ReadingBookCase existingBookCase = readingBookCaseRepository.findByUserAndBookData(user, bookData);

        if (existingBookCase != null) {
            // Update the existing chapter and position
            existingBookCase.setChapterName(request.getChapterName());
            existingBookCase.setPositionReading(request.getPositionReading());
            existingBookCase.setReadingDate(request.getReadingDate());
            // Save the updated book case
            ReadingBookCase updatedReadingBookCase = readingBookCaseRepository.save(existingBookCase);
            return readingBookCaseMapper.toReadingBookCaseResponse(updatedReadingBookCase);
        } else {
            // If it doesn't exist, create a new ReadingBookCase
            ReadingBookCase readingBookCase = readingBookCaseMapper.toReadingBookCase(request);
            readingBookCase.setUser(user);
            readingBookCase.setBookData(bookData);

            ReadingBookCase savedReadingBookCase = readingBookCaseRepository.save(readingBookCase);
            return readingBookCaseMapper.toReadingBookCaseResponse(savedReadingBookCase);
        }
    }

    public List<ReadingBookCaseResponse> getAllReadingBooksByUserId(String userId) {
        List<ReadingBookCase> readingBooks = readingBookCaseRepository.findByUser_Uid(userId);
        return readingBooks.stream()
                .map(readingBookCaseMapper::toReadingBookCaseResponse)
                .collect(Collectors.toList());
    }


    @Transactional
    public void deleteReadingBookCase(String id) {
        readingBookCaseRepository.deleteById(id);
    }

}
