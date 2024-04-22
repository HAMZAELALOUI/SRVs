package com.project.srv.jwt;

import java.security.SecureRandom;
import java.util.Base64;

public class SecretKeyGenerator {
    public static void main(String[] args) {
        SecureRandom random = new SecureRandom();
        byte[] keyBytes = new byte[32];  // 256-bit key
        random.nextBytes(keyBytes);
        String encodedKey = Base64.getEncoder().encodeToString(keyBytes);
        System.out.println("Secret Key: " + encodedKey);
    }
}

