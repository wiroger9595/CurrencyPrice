package com.callcoindesk.currencycheck.validation;

import java.util.Arrays;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;



public class ValidateSequenceImpl implements ConstraintValidator<ValidateSequence, String> {
	
	
	@Override
    public void initialize(ValidateSequence constraintAnnotation) {
    }

	@Override
	public boolean isValid(String passwordValue, ConstraintValidatorContext context) {
		
	    List<String> checkPasswords = Arrays.asList(passwordValue);

		
		/**
		 * 
		 * password can not be continuous sequence
		 * 
		 */
	    final String regex = "(\\d+?)\\1";
	    Pattern p = Pattern.compile(regex);
	    for (String elem : checkPasswords) {
	        Matcher matcher = p.matcher(elem);
	        while (matcher.find()) {
	        	return false;
	        }
	    }
		
		return true;
	}

}
