package com.shop.helper;

import com.github.javafaker.Faker;
import org.apache.commons.codec.digest.DigestUtils;

public class PasswordHelper {

    private final Faker faker = new Faker();

    public String hash(String value) {
        return DigestUtils.md5Hex(value);
    }

    public String generateSalt() {
        return faker.random().hex(8);
    }

    public boolean isPasswordValid(String password) {
        return !(password == null ||
                password.isBlank() ||
                password.length() < 5 ||
                password.contains(" "));
    }

}
