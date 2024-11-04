package com.example.backend.service;

import java.util.HashSet;
import java.util.List;

import com.example.backend.dto.request.UserCreationRequest;
import com.example.backend.dto.request.UserUpdateRequest;
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

        userMapper.updateUser(user, request);
        user.setPassword(passwordEncoder.encode(request.getPassword()));

        var role = roleRepository.findAllById(request.getRoles());

        user.setRoles(new HashSet<>(role));

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

    public UserResponse emailExist(String email) {
        return userMapper.toUserResponse(userRepository.findByEmail(email).orElseThrow(() -> new RuntimeException("Email not found")));
    }

    public UserResponse getMyInfo() {
        var context = SecurityContextHolder.getContext();
        String name = context.getAuthentication().getName();
        User user = userRepository.findByEmail(name).orElseThrow(() -> new AppException(ErrorCode.EMAIL_NOT_EXIST));
        return userMapper.toUserResponse(user);
    }
}
