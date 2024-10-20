package com.example.backend.dto.request;

import java.util.Date;
import java.util.List;
import java.util.Set;

import com.example.backend.dto.response.RoleResponse;
import com.example.backend.validator.PhoneValid;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@FieldDefaults(level=AccessLevel.PRIVATE)

public class UserCreationRequest {

    String uid;

    @Size(min=1,message="DISPLAY_NAME_VALID_MIN")
    @Size(max=50,message="DISPLAY_NAME_VALID_MAX")
    String displayName;

    @Email(message="EMAIL_VALID_FORMAT")
    String email;

    @Size(min=6,message="PASSWORD_VALID_MIN")
    @Size(max=20,message="PASSWORD_VALID_MAX")
    String password;

    String photoURL;
    
    Date creationTime;

    List<String> roles; // Thêm trường roles
}
