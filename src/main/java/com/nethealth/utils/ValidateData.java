package com.nethealth.utils;

import java.util.Set;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.ValidationException;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;

import com.nethealth.utils.exception.ConditionException;
import com.nethealth.utils.exception.ObjectDataBaseException;
import com.nethealth.utils.exception.ObjectTokenException;

public class ValidateData {

	public static void validatorBean(Object object) throws ValidationException{
		ValidatorFactory validatorFactory = Validation.buildDefaultValidatorFactory();
		Validator validator = validatorFactory.getValidator();
		Set<ConstraintViolation<Object>> constraintViolations = validator.validate(object);
		if (constraintViolations.size() > 0 ) {
			throw new ValidationException("validation impossible");
		}
	
	}
	
	
	public static void validatorCondition(String message, boolean condition) throws ConditionException{
		if(condition == false) {
			throw new ConditionException(message);
		}
	}
	
	public static void validatorObjectDataBase(String message , Object ...objects) throws ObjectDataBaseException{
		for(int i = 0 ; i < objects.length ; i++) {
			if(objects[i] != null) {
				throw new ObjectDataBaseException(message);
			}
		}
	}
	
	public static void validatorObjectToken(String message , Object object) throws ObjectTokenException{
		if(object == null) {
				throw new ObjectTokenException(message);
		}
		
	}

}
