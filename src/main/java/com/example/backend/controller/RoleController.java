package com.example.backend.controller;


import com.example.backend.dto.request.ApiResponse;
import com.example.backend.dto.request.RoleRequest;
import com.example.backend.dto.response.RoleResponse;
import com.example.backend.service.RoleService;
import lombok.AccessLevel;
import lombok.Data;
import lombok.experimental.FieldDefaults;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/roles")
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@Data
@CrossOrigin(origins = "http://localhost:3000")
public class RoleController {

    RoleService roleService;

    @PostMapping
    ApiResponse<RoleResponse> create(@RequestBody RoleRequest request) {
        return ApiResponse.<RoleResponse>builder()
                .result(roleService.createRole(request))
                .build();
    }

    @GetMapping
    ApiResponse<List<RoleResponse>> getAll() {
        return ApiResponse.<List<RoleResponse>>builder()
                .result(roleService.getAllRoles())
                .build();
    }


    @GetMapping("/{id}")
    ApiResponse<RoleResponse> getById(@PathVariable String id) {
        return ApiResponse.<RoleResponse>builder()
                .result(roleService.getRoleById(id))
                .build();
    }

    @PutMapping("/{id}")
    ApiResponse<RoleResponse> updateRole(@PathVariable String id, @RequestBody RoleRequest request) {
        return ApiResponse.<RoleResponse>builder()
                .result(roleService.updateRole(id, request))
                .build();
    }

    @DeleteMapping("/{role}")
    ApiResponse<Void> deleteRole(@PathVariable String role) {
        roleService.deleteRole(role);
        return ApiResponse.<Void>builder()
                .build();
    }
}
