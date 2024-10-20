package com.example.backend.mapper;

import com.example.backend.dto.response.ComicResponse;
import com.example.backend.entity.Comic;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface ComicMapper {

//    @Mapping(target = "title", source = "title")
    ComicResponse toComicResponse(Comic comic);
}
