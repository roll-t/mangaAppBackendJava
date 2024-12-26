package com.example.backend.service;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.stream.Collectors;

import com.example.backend.dto.request.UserCreationRequest;
import com.example.backend.dto.request.UserUpdateRequest;
import com.example.backend.dto.response.TopAuthResponse;
import com.example.backend.dto.response.UserResponse;
import com.example.backend.entity.Role;
import com.example.backend.entity.User;
import com.example.backend.enums.RoleType;
import com.example.backend.exception.AppException;
import com.example.backend.exception.ErrorCode;
import com.example.backend.mapper.UserMapper;
import com.example.backend.repository.RoleRepository;
import com.example.backend.repository.UserRepository;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.access.prepost.PostAuthorize;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;


@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@Slf4j
public class UserService {

    UserRepository userRepository;

    UserMapper userMapper;

    RoleRepository roleRepository;

    PasswordEncoder passwordEncoder;


    public UserResponse createUser(UserCreationRequest request) {
        // Kiểm tra nếu email đã tồn tại
        if (userRepository.existsByEmail(request.getEmail())) {
            throw new AppException(ErrorCode.EMAIL_EXISTED);
        }

        // Chuyển đổi UserCreationRequest thành User entity
        User user = userMapper.toUser(request);
        user.setPassword(passwordEncoder.encode(request.getPassword()));

        // Tạo HashSet để chứa các đối tượng Role
        HashSet<Role> roles = new HashSet<>();

        // Tìm và thêm các đối tượng Role từ cơ sở dữ liệu
        for (String roleName : request.getRoles()) {
            Role role = roleRepository.findByName(roleName)
                    .orElseThrow(() -> new RuntimeException("Role not found: " + roleName));
            roles.add(role);
        }

        if (request.getRoles().isEmpty()) {
            Role rolesType = roleRepository.findByName(RoleType.USER.name())
                    .orElseThrow(() -> new RuntimeException("Admin role not found"));
            roles.add(rolesType);
        }

        user.setRoles(roles);

        return userMapper.toUserResponse(userRepository.save(user));
    }

    public UserResponse updateUser(String uid, UserUpdateRequest request) {
        User user = userRepository.findById(uid).orElseThrow(() -> new RuntimeException("User not found"));

        if (request.getDisplayName() != null) {
            user.setDisplayName(request.getDisplayName());
        }

        if (request.getPassword() != null && !request.getPassword().isEmpty()) {
            user.setPassword(passwordEncoder.encode(request.getPassword()));
        }

        if (request.getPhotoURL() != null) {
            user.setPhotoURL(request.getPhotoURL());
        }

        if (request.getRoles() != null && !request.getRoles().isEmpty()) {
            var roles = roleRepository.findAllById(request.getRoles());
            user.setRoles(new HashSet<>(roles));
        }
        return userMapper.toUserResponse(userRepository.save(user));
    }


    public void delete(String uid) {
        userRepository.deleteById(uid);
    }


    // Uncomment this line to enforce that only users with the ADMIN role can access this method
//    @PreAuthorize("hasRole('ADMIN')")
    public List<UserResponse> getUsers() {

        // Retrieve all users from the repository
        List<User> users = userRepository.findAll();

        // If no users are found, throw a runtime exception
        if (users.isEmpty()) {
            throw new RuntimeException("No users found");
        }

        // Convert the list of User entities to UserResponse DTOs
        return userMapper.toUserResponseList(users);
    }

    // kiểm tra nếu đúng là user đang đăng nhập thì cho thông qua
//    @PostAuthorize("returnObject.email==authentication.name")// vào chạy method rồi mới kiểm tra
    public UserResponse getUserById(String id) {
        log.info("in method get user by id");
        System.out.print(userMapper.toUserResponse(userRepository.findById(id).orElseThrow(() -> new RuntimeException("User not found"))));
        return userMapper.toUserResponse(userRepository.findById(id).orElseThrow(() -> new RuntimeException("User not found")));
    }

    public List<UserResponse> getUsersByRole(String roleName) {
        Role role = roleRepository.findByName(roleName)
                .orElseThrow(() -> new RuntimeException("Role not found: " + roleName));

        List<User> users = userRepository.findByRolesContaining(role);

        if (users.isEmpty()) {
            throw new RuntimeException("No users found for role: " + roleName);
        }

        return userMapper.toUserResponseList(users);
    }


    public UserResponse emailExist(String email) {
        return userMapper.toUserResponse(userRepository.findByEmail(email).orElseThrow(() -> new RuntimeException("Email not found")));
    }

    public UserResponse getMyInfo() {
        var context = SecurityContextHolder.getContext();
        String name = context.getAuthentication().getName();
        User user = userRepository.findByEmail(name).orElseThrow(() -> new AppException(ErrorCode.EMAIL_NOT_EXIST));
        return userMapper.toUserResponse(user);
    }

    // UserService.java
    public List<UserResponse> getUsersByPermission(String permission) {
        List<User> users = userRepository.findAll();

        List<User> filteredUsers = users.stream()
                .filter(user -> user.getRoles().stream()
                        .anyMatch(role -> role.getPermissions().stream()
                                .anyMatch(permissionObj -> permissionObj.getName().equals(permission))))
                .collect(Collectors.toList());

        if (filteredUsers.isEmpty()) {
            throw new RuntimeException("No users found with permission: " + permission);
        }

        return userMapper.toUserResponseList(filteredUsers);
    }

    public List<UserResponse> getUserStatisticsWeekly() {
        // Xác định khoảng thời gian của tuần qua
        LocalDateTime now = LocalDateTime.now();
        LocalDateTime oneWeekAgo = now.minus(7, ChronoUnit.DAYS);

        // Lấy danh sách người dùng được tạo trong khoảng thời gian này
        List<User> users = userRepository.findUsersCreatedBetween(oneWeekAgo, now);

        // Nếu không tìm thấy người dùng nào, trả về danh sách trống
        if (users.isEmpty()) {
            log.warn("No users created in the last week.");
            return List.of();
        }

        return userMapper.toUserResponseList(users);
    }


    public List<UserResponse> getUserStatisticsMonthly() {
        // Xác định khoảng thời gian của tháng qua
        LocalDateTime now = LocalDateTime.now();
        LocalDateTime oneMonthAgo = now.minus(1, ChronoUnit.MONTHS);

        // Lấy danh sách người dùng được tạo trong khoảng thời gian này
        List<User> users = userRepository.findUsersCreatedBetween(oneMonthAgo, now);

        // Nếu không tìm thấy người dùng nào, trả về danh sách trống
        if (users.isEmpty()) {
            log.warn("No users created in the last month.");
            return List.of();
        }

        // Chuyển đổi từ `User` sang `UserResponse`
        return userMapper.toUserResponseList(users);
    }

    public int getUserCountMonthly() {
        LocalDateTime now = LocalDateTime.now();
        LocalDateTime oneMonthAgo = now.minus(1, ChronoUnit.MONTHS);

        return userRepository.countUsersCreatedBetween(oneMonthAgo, now);
    }

    // tỉ lệ tăng thêm trong tuần
    public double calculateWeeklyGrowthPercentage() {
        // Xác định thời gian của tuần trước
        LocalDateTime startOfLastWeek = LocalDateTime.now()
                .minusWeeks(1)  // Lùi lại 1 tuần
                .with(java.time.temporal.TemporalAdjusters.previousOrSame(java.time.DayOfWeek.MONDAY)) // Thứ Hai của tuần trước
                .toLocalDate().atStartOfDay();
        LocalDateTime endOfLastWeek = startOfLastWeek.plusDays(6);  // Chủ Nhật của tuần trước

        // Xác định thời gian của tuần này
        LocalDateTime startOfThisWeek = LocalDateTime.now()
                .with(java.time.temporal.TemporalAdjusters.previousOrSame(java.time.DayOfWeek.MONDAY))  // Thứ Hai của tuần này
                .toLocalDate().atStartOfDay();
        LocalDateTime endOfThisWeek = startOfThisWeek.plusDays(6);  // Chủ Nhật của tuần này

        // Đếm số người dùng tạo trong tuần trước và tuần này
        long usersLastWeek = userRepository.countUsersCreatedBetween(startOfLastWeek, endOfLastWeek);
        long usersThisWeek = userRepository.countUsersCreatedBetween(startOfThisWeek, LocalDateTime.now());

        // Tính phần trăm tăng trưởng
        if (usersLastWeek == 0) {
            return 0;
        }

        double growthPercentage = ((double) usersThisWeek - usersLastWeek) / usersLastWeek * 100;
        return growthPercentage;
    }


    public double calculateWeeklyGrowthPercentageBasedOnTotal() {
        // Lấy thời gian bắt đầu tuần hiện tại
        LocalDateTime now = LocalDateTime.now();
        LocalDateTime startOfCurrentWeek = now.minus(7, ChronoUnit.DAYS);

        // Đếm số lượng người dùng mới trong tuần hiện tại
        int currentWeekCount = userRepository.countUsersCreatedBetween(startOfCurrentWeek, now);

        // Đếm tổng số người dùng
        long totalUserCount = userRepository.count();

        // Xử lý trường hợp đặc biệt: nếu tổng số người dùng là 0
        if (totalUserCount == 0) {
            return 0.0; // Không có người dùng, không có tăng trưởng
        }

        // Tính toán tỷ lệ phần trăm tăng trưởng dựa trên tổng số người dùng
        double growthPercentage = ((double) currentWeekCount / totalUserCount) * 100;

        return growthPercentage;
    }


    // tỉ lệ tăng thêm trong tháng
    public double calculateMonthlyGrowthPercentage() {
        // Xác định khoảng thời gian tháng hiện tại và tháng trước
        LocalDateTime now = LocalDateTime.now();
        LocalDateTime startOfCurrentMonth = now.minus(1, ChronoUnit.MONTHS);
        LocalDateTime startOfPreviousMonth = startOfCurrentMonth.minus(1, ChronoUnit.MONTHS);

        // Lấy số lượng người dùng mới theo tháng hiện tại và tháng trước
        int currentMonthCount = userRepository.countUsersCreatedBetween(startOfCurrentMonth, now);
        int previousMonthCount = userRepository.countUsersCreatedBetween(startOfPreviousMonth, startOfCurrentMonth);

        // Tính phần trăm tăng trưởng
        if (previousMonthCount == 0) {
            return currentMonthCount > 0 ? 100.0 : 0.0; // Tránh chia cho 0
        }
        return ((double) (currentMonthCount - previousMonthCount) / previousMonthCount) * 100;
    }

    public List<TopAuthResponse> getTop10UsersWithMostBooks() {
        Pageable pageable = PageRequest.of(0, 10); // Lấy 10 kết quả đầu tiên

        List<User> results = userRepository.findTop10UsersWithMostBooks(pageable).getContent();

        return results.stream()
                .map(user -> {
                    // Đếm số sách của tác giả
                    long bookCount =(long) user.getBooks().size();
                    return new TopAuthResponse(user, bookCount);
                })
                .collect(Collectors.toList());
    }
}
