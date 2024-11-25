package com.example.backend.mapper;

import com.example.backend.dto.request.RoleRequest;
import com.example.backend.dto.response.RoleResponse;
import com.example.backend.entity.Permission;
import com.example.backend.entity.Role;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

import java.util.Set;
import java.util.stream.Collectors;

@Mapper(componentModel = "spring", uses = PermissionMapper.class)
public interface RoleMapper {

    RoleMapper INSTANCE = Mappers.getMapper(RoleMapper.class);

    @Mapping(target = "name", source = "name")
    @Mapping(target = "description", source = "description")
    @Mapping(target = "permissions", source = "permissions")
        // Ánh xạ permissions với PermissionMapper
    RoleResponse toRoleResponse(Role role);

    @Mapping(target = "permissions", source = "permissions")
    Role toRole(RoleRequest request);

    // Phương thức ánh xạ tùy chỉnh từ Set<String> sang Set<Permission>
    default Set<Permission> mapPermissions(Set<String> permissions) {
        return permissions.stream()
                .map(permissionName -> {
                    Permission permission = new Permission();
                    permission.setName(permissionName);
                    return permission;
                })
                .collect(Collectors.toSet());
    }
}
