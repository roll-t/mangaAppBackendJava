package com.example.backend.mapper;

import com.example.backend.dto.request.CategoryRequest;
import com.example.backend.dto.request.ChapterRequest;
import com.example.backend.dto.request.ChapterUpdateRequest;
import com.example.backend.dto.response.ChapterResponse;
import com.example.backend.entity.Category;
import com.example.backend.entity.Chapter;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface ChapterMapper {

    Chapter toChapter(ChapterRequest chapterRequest);

    @Mapping(source = "chapter.bookData.bookDataId", target = "bookDataId")
    ChapterResponse toChapterResponse(Chapter chapter);

    ChapterUpdateRequest toChapterUpdateRequest(ChapterRequest chapterRequest);

    void chapterUpdate(@MappingTarget Chapter chapter, ChapterUpdateRequest chapterUpdateRequest);
}
