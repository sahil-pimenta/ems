package com.project.ems.utils;

import java.util.regex.Pattern;

public class EmailValidator {
	
    private static final Pattern EMAIL_PATTERN = Pattern.compile(
            "^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}$"
        );

        public static boolean isValidEmail(String email) {
            if (email == null || email.isBlank()) {
                return false;
            }
            return EMAIL_PATTERN.matcher(email).matches();
        }
}
