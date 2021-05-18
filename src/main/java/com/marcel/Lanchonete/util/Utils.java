package com.marcel.Lanchonete.util;

import java.text.Normalizer;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;
import org.apache.commons.lang3.RandomStringUtils;

@Component
public class Utils {
    public String encodePassword(String password) {
        BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();
        return bCryptPasswordEncoder.encode(password);
    }

    public boolean matchPassword(String password1, String password2) {
        BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();
        return bCryptPasswordEncoder.matches(password1, password2);
    }

    public String removeAcentos(String str) {
        return Normalizer.normalize(str, Normalizer.Form.NFD).replaceAll("[^\\p{ASCII}]", "");
    }

    public String generateRandomCode() {
        return RandomStringUtils.randomAlphanumeric(5).toUpperCase();
    }
}
