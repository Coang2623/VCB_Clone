package com.vcb.backend.validator.dobValidator;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.FIELD;

@Target({FIELD}) //Áp dụng ở đâu
@Retention(RetentionPolicy.RUNTIME) //Xử lý khi nào
@Constraint(validatedBy = {DobValidator.class}) //Kiểm tra
public @interface DobConstraint {
    String message() default "Invalid date of birth";

    //Min age accepted
    int min();

    Class<?>[] groups() default { };

    Class<? extends Payload>[] payload() default { };
}
