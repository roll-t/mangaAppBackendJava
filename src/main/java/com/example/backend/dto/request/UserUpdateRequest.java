package com.example.backend.dto.request;

import com.example.backend.validator.PhoneValid;
import jakarta.validation.constraints.Size;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;

import java.util.List;

@Data
@FieldDefaults(level=AccessLevel.PRIVATE)
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserUpdateRequest {

    String displayName;

    @Size(min=6,message="PASSWORD_VALID_MIN")
    @Size(max=20,message="PASSWORD_VALID_MAX")
    String password;

    String photoURL;

    @PhoneValid(message = "PHONE_VALID_FORMAT")
    String phoneNumber;

    List<String> roles;
}
