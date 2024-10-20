package com.example.backend.dto.response;


import lombok.*;
import lombok.experimental.FieldDefaults;

import java.util.Date;
import java.util.Set;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)

public class UserResponse {
    String uid;
    String displayName;
    String email;
    String photoURL;    
    Date creationTime;
    Set<RoleResponse> roles;
}
