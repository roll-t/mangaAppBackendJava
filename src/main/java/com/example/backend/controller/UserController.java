package com.example.backend.controller;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.List;

import com.example.backend.dto.request.ApiResponse;
import com.example.backend.dto.request.UserCreationRequest;
import com.example.backend.dto.request.UserUpdateRequest;
import com.example.backend.dto.response.UserResponse;
import com.example.backend.repository.UserRepository;
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
    UserRepository userRepository;

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

    @GetMapping("/statistics/monthly")
    public ApiResponse<List<UserResponse>> getUserStatisticsMonthly() {
        try {
            List<UserResponse> statistics = userService.getUserStatisticsMonthly();
            return ApiResponse.<List<UserResponse>>builder()
                    .result(statistics)
                    .build();
        } catch (Exception e) {
            log.error("Error fetching monthly user statistics", e);
            return ApiResponse.<List<UserResponse>>builder()
                    .code(1)
                    .message("Error fetching monthly user statistics")
                    .build();
        }
    }


    @GetMapping("/statistics/weekly")
    public ApiResponse<List<UserResponse>> getUserStatisticsWeekly() {
        try {
            List<UserResponse> statistics = userService.getUserStatisticsWeekly();
            return ApiResponse.<List<UserResponse>>builder()
                    .result(statistics)
                    .build();
        } catch (Exception e) {
            log.error("Error fetching weekly user statistics", e);
            return ApiResponse.<List<UserResponse>>builder()
                    .code(1)
                    .message("Error fetching weekly user statistics")
                    .build();
        }
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

    @GetMapping("/permissions/{permission}")
    public ApiResponse<List<UserResponse>> getUsersByPermission(@PathVariable("permission") String permission) {
        try {
            List<UserResponse> users = userService.getUsersByPermission(permission);
            return ApiResponse.<List<UserResponse>>builder()
                    .result(users)
                    .build();
        } catch (Exception e) {
            log.error("Error fetching users by permission", e);
            return ApiResponse.<List<UserResponse>>builder()
                    .code(1) // Assuming a non-zero code indicates an error
                    .message("Error fetching users by permission")
                    .build();
        }
    }

    @GetMapping("/roles/{role}")
    public ApiResponse<List<UserResponse>> getUsersByRole(@PathVariable("role") String role) {
        List<UserResponse> users = userService.getUsersByRole(role);
        return ApiResponse.<List<UserResponse>>builder()
                .result(users)
                .build();
    }

    @GetMapping("/statistics/monthly/count")
    public ApiResponse<Integer> getUserCountMonthly() {
        try {
            int count = userService.getUserCountMonthly();
            return ApiResponse.<Integer>builder()
                    .result(count)
                    .build();
        } catch (Exception e) {
            log.error("Error fetching monthly user count", e);
            return ApiResponse.<Integer>builder()
                    .code(1)
                    .message("Error fetching monthly user count")
                    .build();
        }
    }

    @GetMapping("/weekly-growth-percentage")
    public ApiResponse<Double> getWeeklyGrowthPercentageBasedOnTotal() {
        double growthPercentage = userService.calculateWeeklyGrowthPercentageBasedOnTotal();
        return ApiResponse.<Double>builder()
                .result(growthPercentage)
                .build();
    }

    @GetMapping("/statistics/weekly-growth")
    public ApiResponse<Double> getWeeklyGrowthPercentage() {
        try {
            double growthPercentage = userService.calculateWeeklyGrowthPercentage();
            return ApiResponse.<Double>builder()
                    .result(growthPercentage)
                    .build();
        } catch (Exception e) {
            log.error("Error calculating weekly growth percentage", e);
            return ApiResponse.<Double>builder()
                    .code(1)
                    .message("Error calculating weekly growth percentage")
                    .build();
        }
    }

    @GetMapping("/statistics/monthly-growth")
    public ApiResponse<Double> getMonthlyGrowthPercentage() {
        try {
            double growthPercentage = userService.calculateMonthlyGrowthPercentage();
            return ApiResponse.<Double>builder()
                    .result(growthPercentage)
                    .build();
        } catch (Exception e) {
            log.error("Error calculating monthly growth percentage", e);
            return ApiResponse.<Double>builder()
                    .code(1)
                    .message("Error calculating monthly growth percentage")
                    .build();
        }
    }
}