package com.example.backend.mapper;

import com.example.backend.dto.request.CommentComicRequest;
import com.example.backend.dto.response.CommentComicResponse;
import com.example.backend.dto.response.CommentResponse;
import com.example.backend.entity.CommentComic;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface CommentComicMapper {

    CommentComic toCommentComic(CommentComicRequest request);
    @Mapping(target = "userId", source = "user.uid") // Assuming User has a method getId()
    CommentComicResponse toCommentComicResponse(CommentComic commentComic);

    CommentResponse toCommentResponse(CommentComic commentComic);
}
