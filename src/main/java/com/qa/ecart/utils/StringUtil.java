package com.qa.ecart.utils;

public class StringUtil {
	
	public static String generateEmail() {
		
		String email = "SeleniumAuto" + Math.random() + "@gmail.com";
		return email;
	}
}
