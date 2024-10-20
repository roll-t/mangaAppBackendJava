package com.example.backend.exception;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.experimental.FieldDefaults;
import org.springframework.http.HttpStatus;

@Getter
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public enum ErrorCode {

    INVALID_ID_KEY (1001,"Invalid message key", HttpStatus.INTERNAL_SERVER_ERROR), //500
    UNCATEGORIZED_EXCEPTION(9999,"Uncategorized exception",HttpStatus.BAD_REQUEST), //400
    EMAIL_EXISTED(1002,"Email existed",HttpStatus.BAD_REQUEST), //400
    EMAIL_VALID_FORMAT(1003,"Email is not valid",HttpStatus.BAD_REQUEST), //400
    PHONE_VALID_FORMAT(1011,"invalid phone number",HttpStatus.BAD_REQUEST), //400
    DISPLAY_NAME_VALID_MIN(1004,"Display name must be at least {min} characters",HttpStatus.BAD_REQUEST), //400
    DISPLAY_NAME_VALID_MAX(1005,"Display name must be no more than {max} characters",HttpStatus.BAD_REQUEST), //400
    PASSWORD_VALID_MIN(1006,"Password must be at least {min} characters",HttpStatus.BAD_REQUEST), //400
    PASSWORD_VALID_MAX(1007,"Password must be no more than 50 characters",HttpStatus.BAD_REQUEST), //400
    EMAIL_NOT_EXIST(1008,"Email not exist",HttpStatus.NOT_FOUND), //404
    UNAUTHENTICATED(1009,"Unauthenticated",HttpStatus.UNAUTHORIZED), //401
    UNAUTHORIZED(1010,"You do not have permission",HttpStatus.FORBIDDEN), //403

    ;

    ErrorCode(int code, String message, HttpStatus statusCode) {
        this.code = code;
        this.message = message;
        this.statusCode = statusCode;
    }

    int code;
    String message;
    HttpStatus statusCode;
}
