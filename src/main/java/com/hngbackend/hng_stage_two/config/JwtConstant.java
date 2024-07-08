package com.hngbackend.hng_stage_two.config;

import org.springframework.beans.factory.annotation.Value;

public class JwtConstant {
	@Value("${jwt.secret}")
	private static String secretKey;
	
	public static final String SECRET_KEY = "dnamlaaxueuqwertyasdfghjklxcvbnmuiopehehejedbadubdhkabhbdkbdakadbfhdb";
}
