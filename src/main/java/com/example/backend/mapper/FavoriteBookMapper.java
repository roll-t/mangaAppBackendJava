package com.example.backend.mapper;

import com.example.backend.dto.request.FavoriteRequest;
import com.example.backend.dto.response.FavoriteResponse;
import com.example.backend.entity.FavoriteBook;
import com.example.backend.entity.BookData;
import com.example.backend.entity.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

import java.time.LocalDateTime;

@Mapper(componentModel = "spring")
public interface FavoriteBookMapper {
    // Convert FavoriteBookRequest to FavoriteBook entity
    @Mapping(target = "favoriteDate", expression = "java(java.time.LocalDateTime.now())")
    // Set current date as favoriteDate
    @Mapping(target = "bookData", source = "bookData")
    @Mapping(target = "bookData.comments", ignore = true) // Book data reference
    @Mapping(target = "user", source = "user")
    // User reference
    FavoriteBook toFavoriteBook(FavoriteRequest request, BookData bookData, User user);


    // Convert FavoriteBook entity to FavoriteBookResponse
    @Mapping(target = "id", source = "id")               // Map id
    @Mapping(target = "bookData", source = "bookData")  // Map bookDataId from the bookData entity
    @Mapping(target = "bookData.comments", ignore = true)  // Map bookDataId from the bookData entity
    @Mapping(target = "userId", source = "user.uid")     // Map userId from the user entity
    @Mapping(target = "favoriteDate", source = "favoriteDate")
    // Map the favoriteDate field
    FavoriteResponse toFavoriteBookResponse(FavoriteBook favoriteBook);

}
