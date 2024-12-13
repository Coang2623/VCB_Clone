package com.vcb.backend.validator.enumValidator;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class EnumValidator implements ConstraintValidator<EnumConstraint, String> {

  private Class<? extends Enum<?>> enumClass;

  @Override
  public void initialize(EnumConstraint constraintAnnotation) {
    this.enumClass = constraintAnnotation.enumClass();
  }

  @Override
  public boolean isValid(String value, ConstraintValidatorContext context) {
    if (value == null) {
      return true;
    }

    // Kiểm tra giá trị có tồn tại trong enum không
    Enum<?>[] enumValues = enumClass.getEnumConstants();
    for (Enum<?> enumValue : enumValues) {
      if (enumValue.name().equals(value)) {
        return true;
      }
    }
    return false;
  }
}

