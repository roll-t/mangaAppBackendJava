package com.example.backend.mapper;

import com.example.backend.dto.request.CategoryRequest;
import com.example.backend.dto.response.CategoryResponse;
import com.example.backend.entity.Category;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

import java.util.List;

@Mapper(componentModel = "spring")
public interface CategoryMapper {

    @Mapping(target = "name", source = "name")
    @Mapping(target = "slug", source = "slug")
    @Mapping(target = "id", source = "id")
    CategoryResponse toCategoryResponse(Category category);

    @Mapping(target = "id", source = "id")
    @Mapping(target = "name", source = "name")
    @Mapping(target = "slug", source = "slug")
    Category toCategory(CategoryRequest categoryRequest);

    List<CategoryResponse> toCategoryList(List<Category> categoryList);

    @Mapping(target = "id", ignore = true)
    void updateCategory(@MappingTarget Category category, CategoryRequest categoryRequest);
}
