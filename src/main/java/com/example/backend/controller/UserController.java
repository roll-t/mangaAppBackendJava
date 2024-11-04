package com.example.backend.controller;

import java.util.List;

import com.example.backend.dto.request.ApiResponse;
import com.example.backend.dto.request.UserCreationRequest;
import com.example.backend.dto.request.UserUpdateRequest;
import com.example.backend.dto.response.UserResponse;
import com.example.backend.service.UserService;
import lombok.AccessLevel;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@Data
@CrossOrigin(origins = "http://localhost:3000")
public class UserController {

    private static final Logger log = LoggerFactory.getLogger(UserController.class);
    UserService userService;

    @PostMapping
    public ApiResponse<UserResponse> createUser(@RequestBody @Valid UserCreationRequest request) {
        return ApiResponse.<UserResponse>builder()
                .result(userService.createUser(request))
                .build();
    }

    @GetMapping
    public ApiResponse<List<UserResponse>> getUsers() {
        try {
            var authentication = SecurityContextHolder.getContext().getAuthentication();
            authentication.getAuthorities().forEach(grantedAuthority -> log.info(grantedAuthority.getAuthority()));

            List<UserResponse> users = userService.getUsers();
            return ApiResponse.<List<UserResponse>>builder()
                    .result(users)
                    .build();
        } catch (Exception e) {
            log.error("Error fetching users", e);
            return ApiResponse.<List<UserResponse>>builder()
                    .code(1) // Assuming a non-zero code indicates an error
                    .message("Error fetching users")
                    .build();
        }
    }

    @GetMapping("/{uid}")
    public ApiResponse<UserResponse> getUserById(@PathVariable("uid") String uid) {
        System.out.print("=====================================================");
        return ApiResponse.<UserResponse>builder()
                .result(userService.getUserById(uid))
                .build();
    }

    @GetMapping("/email-exist/{email}")
    public ApiResponse<UserResponse> checkEmailExist(@PathVariable String email) {
        return ApiResponse.<UserResponse>builder()
                .result(userService.emailExist(email))
                .build();
    }


    @GetMapping("/my-info")
    public ApiResponse<UserResponse> getMyInfo() {
        return ApiResponse.<UserResponse>builder()
                .result(userService.getMyInfo())
                .build();
    }


    @PutMapping("/{uid}")
    public ApiResponse<UserResponse> updateUser(@PathVariable("uid") String uid, @RequestBody UserUpdateRequest request) {
        return ApiResponse.<UserResponse>builder()
                .result(userService.updateUser(uid, request))
                .build();
    }

    @DeleteMapping("/{uid}")
    public ApiResponse<String> deleteUser(@PathVariable String uid) {
        try {
            userService.delete(uid);
            return ApiResponse.<String>builder()
                    .result("User has been deleted")
                    .build();
        } catch (Exception e) {
            return ApiResponse.<String>builder()
                    .result("Delete failed: error " + e.getMessage())
                    .build();
        }
    }
}
