package com.callcoindesk.currencycheck.validation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import javax.validation.Constraint;
import javax.validation.Payload;

@Target({ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = {ValidateSequenceImpl.class})
public @interface ValidateSequence {
	
	
	String message() default "password can't be continous sequence";
	
	Class<?>[] groups() default {};
	
    Class<? extends Payload>[] payload() default {};


}
