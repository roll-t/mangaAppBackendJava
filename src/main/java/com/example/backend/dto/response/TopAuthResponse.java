package com.example.backend.dto.response;

import com.example.backend.entity.User;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class TopAuthResponse {
    private User user;
    private Long bookCount;
}
