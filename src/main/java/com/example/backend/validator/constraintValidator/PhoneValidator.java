package com.example.backend.validator.constraintValidator;

import com.example.backend.validator.PhoneValid;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class PhoneValidator implements ConstraintValidator<PhoneValid,String> {

    private static final String PHONE_REGEX = "^(0?)(3[2-9]|5[6|8|9]|7[0|6-9]|8[1-9]|9[0-4|6-9])[0-9]{7}$";


    // hàm khởi tạo
    @Override
    public void initialize(PhoneValid constraintAnnotation) {
        ConstraintValidator.super.initialize(constraintAnnotation);
    }

    @Override
    public boolean isValid(String phoneField, ConstraintValidatorContext context) {
        if(phoneField==null){
            return false;
        }
        return phoneField.trim().matches(PHONE_REGEX);
    }
}
