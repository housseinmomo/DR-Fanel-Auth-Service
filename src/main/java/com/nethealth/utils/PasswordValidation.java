package com.nethealth.utils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class PasswordValidation {
	
	public static final Pattern VALID_PASSWORD_REGEX = Pattern.compile("^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=\\S+$).{8,}$");
	
	
	public static boolean validate(String password) {
        Matcher matcher = VALID_PASSWORD_REGEX.matcher(password);
        return matcher.find();
	}
	
	public static boolean validatePassword(String password) {
		Matcher matcher = VALID_PASSWORD_REGEX.matcher(password);
		if(matcher.find()) {
			return true;
		}
		return false;
	}
	
	
	
	

}
