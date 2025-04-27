package com.Assessment.Library_management.util;

import lombok.extern.log4j.Log4j2;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.UUID;

@Log4j2
public class Utility {
    public static String getBCryptPasswordEncoder(PasswordEncoder passwordEncoder, String password) {
        return passwordEncoder.encode(password);
    }

    public static String generateUserId() {
        String uuId = UUID.randomUUID().toString().replace("-", "").toUpperCase();
        return uuId;
    }
}
