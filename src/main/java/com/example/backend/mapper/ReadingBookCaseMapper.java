package com.example.backend.mapper;

import com.example.backend.dto.request.ReadingBookCaseRequest;
import com.example.backend.dto.response.ReadingBookCaseResponse;
import com.example.backend.entity.ReadingBookCase;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Mapper(componentModel = "spring")
public interface ReadingBookCaseMapper {
    // Ánh xạ từ ReadingBookCaseRequest đến ReadingBookCase entity
    @Mapping(target = "user", ignore = true)
    @Mapping(target = "bookData", ignore = true)
    ReadingBookCase toReadingBookCase(ReadingBookCaseRequest request);

    // Ánh xạ từ ReadingBookCase entity đến ReadingBookCaseResponse
    @Mapping(target = "uid", source = "user.uid")
    @Mapping(target = "bookDataId", source = "bookData.bookDataId")
    @Mapping(target = "bookData", source = "bookData")
    @Mapping(target = "bookData.comments", ignore = true)
    @Mapping(target = "id", source = "id")
    ReadingBookCaseResponse toReadingBookCaseResponse(ReadingBookCase readingBookCase);


    @Mapping(target = "uid", source = "user.uid")
    @Mapping(target = "bookDataId", source = "bookData.bookDataId")
    @Mapping(target = "bookData", source = "bookData")
    @Mapping(target = "bookData.comments", ignore = true)
    @Mapping(target = "id", source = "id")
    @Mapping(target = "chapterName", source = "chapterName")
    @Mapping(target = "readingDate", source = "readingDate")
    @Mapping(target = "positionReading", source = "positionReading")
    ReadingBookCaseResponse toReadingBookCaseResponseWithoutComments(ReadingBookCase readingBookCase);
}