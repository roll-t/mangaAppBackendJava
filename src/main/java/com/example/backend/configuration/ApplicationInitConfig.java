package com.example.backend.configuration;

import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
import com.example.backend.entity.User;
import com.example.backend.enums.RoleType;
import com.example.backend.repository.UserRepository;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.ApplicationRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.client.RestTemplate;

import java.util.HashSet;

@Configuration
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PACKAGE,makeFinal = true)
@Slf4j
public class ApplicationInitConfig {

    PasswordEncoder passwordEncoder;

    @Bean
    ApplicationRunner applicationRunner(UserRepository userRepository){
        return args -> {
            if(userRepository.findByEmail("admin@gmail.com").isEmpty()){
                var roles = new HashSet<String>();
                roles.add(RoleType.ADMIN.name());
                User user = User.builder()
                        .email("admin@gmail.com")
                        .password(passwordEncoder.encode("admin@gmail.com"))
                        .build();

                userRepository.save(user);
                log.warn("default admin user has been created with default password:admin@gmail.com,please change it");
            };
        };
    }

    @Bean
    public RestTemplate restTemplate() {
        return new RestTemplate();
    }

    @Bean
    public Cloudinary cloudinary() {
        return new Cloudinary(ObjectUtils.asMap(
                "cloud_name", "dqpc9qhu5",
                "api_key", "781539129191854",
                "api_secret", "rFzuLb14bUHmN-YlHlr0bFVemkM"
        ));
    }
}
