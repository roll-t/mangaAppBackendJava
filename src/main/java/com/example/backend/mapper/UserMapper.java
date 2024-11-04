package com.example.backend.mapper;

import com.example.backend.dto.request.UserCreationRequest;
import com.example.backend.dto.request.UserUpdateRequest;
import com.example.backend.dto.response.UserResponse;
import com.example.backend.entity.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import java.util.List;

@Mapper(componentModel = "spring")
public interface UserMapper {


    @Mapping(target = "books", ignore = true)
    @Mapping(target = "comments", ignore = true)
    @Mapping(target = "uid", source = "uid")
    @Mapping(target = "roles", ignore = true)
    User toUser(UserCreationRequest request);


    @Mapping(target = "uid", source = "uid")
    UserResponse toUserResponse(User user);

    List<UserResponse> toUserResponseList(List<User> users);

    @Mapping(target = "roles", ignore = true)
    void updateUser(@MappingTarget User user, UserUpdateRequest request);
}
