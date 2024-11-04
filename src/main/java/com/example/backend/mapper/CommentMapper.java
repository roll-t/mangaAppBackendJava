package com.example.backend.mapper;

import com.example.backend.dto.request.CommentComicRequest;
import com.example.backend.dto.request.CommentRequest;
import com.example.backend.dto.response.CommentResponse;
import com.example.backend.entity.Comment;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface CommentMapper {


    @Mapping(target = "user", source = "user")
        // Ensure user mapping
    CommentResponse toResponse(Comment comment);

    @Mapping(target = "bookData", ignore = true)
    Comment toComment(CommentComicRequest request);


    Comment toEntity(CommentRequest request);
}
