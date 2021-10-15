package com.nethealth.utils;

import org.springframework.mail.SimpleMailMessage;


public class SendEmail {
	
	
	
	public static SimpleMailMessage automaticEmail(String email, String confirmationToken, String title, String message) {
		SimpleMailMessage simpleMailMessage = new SimpleMailMessage();
		simpleMailMessage.setTo(email);
		simpleMailMessage.setSubject(title);
		simpleMailMessage.setFrom("sandrine.louise.ekobe@gmail.com");
		simpleMailMessage.setText(message+confirmationToken);
        return simpleMailMessage;
	}
	

}
