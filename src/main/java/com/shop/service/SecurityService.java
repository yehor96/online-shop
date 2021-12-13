package com.shop.service;

import com.github.javafaker.Faker;
import com.shop.entity.User;
import jakarta.servlet.http.Cookie;
import lombok.RequiredArgsConstructor;
import org.apache.commons.codec.digest.DigestUtils;

import java.util.List;
import java.util.Objects;
import java.util.UUID;
import java.util.stream.Stream;

@RequiredArgsConstructor
public class SecurityService {

    public static final String PASSWORD_IS_NOT_VALID_MESSAGE = """
            Provided password does not match the requirements. Your password should:
            <br>- Be 5 or more characters
            <br>- Not be blank
            <br>- Not contain spaces
            """;
    private static final String TOKEN_KEY = "user-token";
    private static final Faker FAKER = new Faker();

    private final List<String> tokenStorage;

    public boolean isLoggedIn(Cookie[] cookies) {
        if (Objects.isNull(cookies)) {
            return false;
        }

        return Stream.of(cookies)
                .anyMatch(cookie -> tokenStorage.contains(cookie.getValue()));
    }

    public Cookie getCookie() {
        String tokenValue = getTokenValue();
        tokenStorage.add(tokenValue);
        return new Cookie(TOKEN_KEY, tokenValue);
    }

    public void addSalt(User user) {
        String salt = generateSalt();
        String saltedPassword = user.getPassword() + salt;
        String hashedSaltedPassword = hash(saltedPassword);

        user.setPassword(hashedSaltedPassword);
        user.setSalt(salt);
    }

    public boolean isPasswordValidForUser(String password, User existingUser) {
        String enteredPassword = hash(password + existingUser.getSalt());
        return enteredPassword.equals(existingUser.getPassword());
    }

    public boolean isPasswordValid(String password) {
        return !(password == null ||
                password.isBlank() ||
                password.length() < 5 ||
                password.contains(" "));
    }

    String hash(String value) {
        return DigestUtils.md5Hex(value);
    }

    String generateSalt() {
        return FAKER.random().hex(8);
    }

    String getTokenValue() {
        return UUID.randomUUID().toString();
    }

}
