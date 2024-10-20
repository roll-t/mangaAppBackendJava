package com.example.backend.service;

import com.example.backend.dto.request.BookModelRequest;
import com.example.backend.dto.response.BookDataResponse;
import com.example.backend.dto.response.BookModelResponse;
import com.example.backend.entity.BookData;
import com.example.backend.entity.BookModel;
import com.example.backend.mapper.BookDataMapper;
import com.example.backend.mapper.BookModelMapper;
import com.example.backend.repository.BookDataRepository;
import com.example.backend.repository.BookModelRepository;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)

public class BookService {

    BookModelMapper bookModelMapper;

    BookModelRepository bookModelRepository;
    public BookModelResponse create(BookModelRequest bookModelRequest) {
        BookModel bookModel = bookModelMapper.toBookModel(bookModelRequest);
        bookModelRepository.save(bookModel);
        return bookModelMapper.toBookModelResponse(bookModel);
    }
}
