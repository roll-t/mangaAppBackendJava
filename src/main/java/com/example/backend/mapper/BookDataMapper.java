package com.example.backend.mapper;

import com.example.backend.dto.request.BookDataRequest;
import com.example.backend.dto.request.BookUpdateRequest;
import com.example.backend.dto.response.BookDataResponse;
import com.example.backend.entity.BookData;
import com.example.backend.entity.Category;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.Named;

import java.util.List;
import java.util.stream.Collectors;

@Mapper(componentModel = "spring")
public interface BookDataMapper {

    BookData toBookData(BookDataRequest bookDataRequest);

    @Mapping(target = "categorySlug", source = "category", qualifiedByName = "categoriesToSlugs")
    BookDataResponse toBookDataResponse(BookData bookData);

    @Named("categoriesToSlugs")
    default List<String> categoriesToSlugs(List<Category> categories) {
        return categories.stream()
                .map(Category::getSlug) // Giả định bạn có phương thức getSlug() trong Category
                .collect(Collectors.toList());
    }

    @Named("categoriesToId")
    default List<Integer> categoriesToId(List<Category> categories) {
        return categories.stream()
                .map(Category::getId) // Giả định bạn có phương thức getId() trong Category
                .collect(Collectors.toList());
    }

    @Mapping(target = "name", source = "name")
    @Mapping(target = "slug", source = "slug")
    @Mapping(target = "status", source = "status")
    @Mapping(target = "thumbUrl", source = "thumbUrl")
    void updateBookDataFromRequest(@MappingTarget BookData bookData, BookUpdateRequest bookUpdateRequest);
}
