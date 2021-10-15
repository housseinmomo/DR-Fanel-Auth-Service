package com.nethealth.security.jwt;

public interface JwtParameters {

	public static final long EXP_TIME = 10*24*60*60*1000; //session duration = 10days
	public static final String SECRET  = "SECRET_KEY"; 
	public static final String PREFIX = "Bearer ";
	public static final String HEADER_STRING = "Authorization"; //
}
