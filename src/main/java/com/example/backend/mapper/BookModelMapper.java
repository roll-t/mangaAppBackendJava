package com.example.backend.mapper;


import com.example.backend.dto.request.BookModelRequest;
import com.example.backend.dto.response.BookModelResponse;
import com.example.backend.entity.BookModel;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface BookModelMapper {

    BookModel toBookModel(BookModelRequest bookModelRequest);

    BookModelResponse toBookModelResponse(BookModel bookModel);
}
