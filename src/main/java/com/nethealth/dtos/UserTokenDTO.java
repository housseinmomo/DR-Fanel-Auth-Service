package com.nethealth.dtos;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor @Getter
public class UserTokenDTO {
	
	private LoginDto loginDto;
	private String jwtToken;

}
