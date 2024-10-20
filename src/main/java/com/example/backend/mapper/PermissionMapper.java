package com.example.backend.mapper;

import com.example.backend.dto.request.PermissionRequest;
import com.example.backend.dto.response.PermissionResponse;
import com.example.backend.entity.Permission;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface PermissionMapper {
    Permission toPermission (PermissionRequest request);

//    @Mapping(target = "name", source = "name")
//    @Mapping(target = "description", source = "description")
    PermissionResponse toPermissionResponse (Permission permission);
}
