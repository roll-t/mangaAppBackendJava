package com.example.backend.validator;

import com.example.backend.validator.constraintValidator.PhoneValidator;
import jakarta.validation.Constraint;
import jakarta.validation.Payload;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.*;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

// annotation được apply ở đâu
@Target({FIELD})
// annotation được xử lý lúc nào
@Retention(RUNTIME) // lúc nhận request
@Constraint(validatedBy = {PhoneValidator.class})
public @interface PhoneValid {

    String message() default "Invalid phone number";

    Class<?>[] groups() default { };

    Class<? extends Payload>[] payload() default { };

}
