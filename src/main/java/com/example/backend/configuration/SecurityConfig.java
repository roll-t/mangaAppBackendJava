package com.example.backend.configuration;

import com.example.backend.enums.RoleType;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationConverter;
import org.springframework.security.oauth2.server.resource.authentication.JwtGrantedAuthoritiesConverter;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity
@FieldDefaults(level = AccessLevel.PRIVATE)
@RequiredArgsConstructor
public class SecurityConfig {

    private final String[] PUBLIC_ENDPOINTS = {
            "/user",
            "/users",
            "/users/{uid}",
            "/users/email-exist/{email}",
            "/auth/token",
            "/auth/introspect",
            "/auth/logout",
            "/auth/refresh",
            "/auth/sign-in-with-google",
            "/comic/home",
            "/comic/detail/{comicName}",
            "/comic/list/{type}",
            "/comic/categories/{slug}",
            "/comic/categories",
            "/comic/search",
            "/category",
            "/categories",
            "/category/{slug}",
            "/comic/search/{slug}",
    };

    final CustomJwtDecoder customJwtDecoder; // Inject CustomJwtDecoder

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .oauth2Login(oauth2 -> oauth2
                        .loginPage("/oauth2/authorization/google")
                )
                .oauth2ResourceServer(oauth2 -> oauth2
                        .jwt(jwtConfigurer -> jwtConfigurer
                                .decoder(customJwtDecoder) // Sử dụng CustomJwtDecoder để giải mã JWT
                                .jwtAuthenticationConverter(jwtConverter()) // Chuyển đổi JWT thành Authentication
                        )
                        .authenticationEntryPoint(new JwtAuthenticationEntryPoint()) // Xử lý lỗi xác thực
                )
                .authorizeHttpRequests(authorizeRequests -> authorizeRequests
                        .requestMatchers(HttpMethod.POST, PUBLIC_ENDPOINTS).permitAll() // Cho phép tất cả POST requests đến PUBLIC_ENDPOINTS
                        .requestMatchers(HttpMethod.GET, PUBLIC_ENDPOINTS).permitAll() // Cho phép tất cả GET requests đến PUBLIC_ENDPOINTS
                        .requestMatchers(HttpMethod.DELETE, PUBLIC_ENDPOINTS).permitAll() // Cho phép tất cả GET requests đến PUBLIC_ENDPOINTS
                        .requestMatchers(HttpMethod.PUT, PUBLIC_ENDPOINTS).permitAll() // Cho phép tất cả GET requests đến PUBLIC_ENDPOINTS
                        .requestMatchers(HttpMethod.GET, "/users").hasRole(RoleType.ADMIN.name()) // Phân quyền cho GET requests đến /users
                        .anyRequest().authenticated() // Yêu cầu xác thực cho tất cả các yêu cầu khác
                )
                .csrf(AbstractHttpConfigurer::disable); // Vô hiệu hóa CSRF

        return http.build();
    }

    @Bean
    JwtAuthenticationConverter jwtConverter() {
        JwtGrantedAuthoritiesConverter jwtGrantedAuthoritiesConverter = new JwtGrantedAuthoritiesConverter();
        jwtGrantedAuthoritiesConverter.setAuthorityPrefix("ROLE_"); // Sử dụng ROLE_ làm tiền tố cho các quyền
        JwtAuthenticationConverter converter = new JwtAuthenticationConverter();
        converter.setJwtGrantedAuthoritiesConverter(jwtGrantedAuthoritiesConverter);
        return converter;
    }

    @Bean
    PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder(10); // Cấu hình BCryptPasswordEncoder với độ mạnh 10
    }
}
