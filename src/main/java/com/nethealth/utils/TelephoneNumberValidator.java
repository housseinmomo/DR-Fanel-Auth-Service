package com.nethealth.utils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class TelephoneNumberValidator {
	
	public static final Pattern TELEPHON_REGEX = Pattern.compile("^[0-9]*$");
	
	
	private static boolean validate(String tel) {
        Matcher matcher = TELEPHON_REGEX.matcher(tel);
        return matcher.find();
	}
	
	public static boolean validateTelephone(String telephone) {
		String result = telephone.replaceAll("\\s+","");
		char firstCharactereTelephone = result.charAt(0);
		int length = result.length();
		if(firstCharactereTelephone == '+') {
			String resultSubstring = result.substring(1);
			if(validate(resultSubstring) && length >= 4 && length <= 15) {
				return true;
			}else {
				return false;
			}
		}else {
			if(validate(result) && length >= 4 && length <= 15) {
				return true;
			}else {
				return false;
			}
		}
	}
	

}
