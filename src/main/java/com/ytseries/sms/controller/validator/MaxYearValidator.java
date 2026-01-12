package com.ytseries.sms.controller.validator;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

import java.time.LocalDate;

public class MaxYearValidator implements ConstraintValidator<MaxYear , LocalDate> {
    private int maxYear;

    @Override
    public void initialize(MaxYear constraintAnnotation) {
        this.maxYear = constraintAnnotation.value();
    }

    @Override
    public boolean isValid(LocalDate value, ConstraintValidatorContext context) {

        if(value == null){
            return true;
        }

        return value.getYear() <= maxYear;
    }
}
