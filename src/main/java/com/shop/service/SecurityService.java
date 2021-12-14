package com.shop.service;

import com.github.javafaker.Faker;
import com.shop.entity.User;
import jakarta.servlet.http.Cookie;
import lombok.RequiredArgsConstructor;
import org.apache.commons.codec.digest.DigestUtils;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

@RequiredArgsConstructor
public class SecurityService {

    public static final String PASSWORD_IS_NOT_VALID_MESSAGE = """
            Provided password does not match the requirements. Your password should:
            <br>- Be 5 or more characters
            <br>- Not be blank
            <br>- Not contain spaces
            """;
    private static final String TOKEN_KEY = "user-os-token";
    private static final Faker FAKER = new Faker();

    private final List<String> tokenStorage = Collections.synchronizedList(new ArrayList<>());
    private final UserService userService;

    //TODO remove after adding Security Filter
    public List<String> getUserTokens(Cookie[] cookies) {
        if (Objects.isNull(cookies)) {
            return Collections.emptyList();
        }

        List<String> userTokens = new ArrayList<>();
        for (Cookie cookie : cookies) {
            if (Objects.equals(cookie.getName(), TOKEN_KEY)) {
                userTokens.add(cookie.getValue());
            }
        }
        return userTokens;
    }

    public boolean isLoggedIn(List<String> userTokens) {
        if (userTokens.isEmpty()) {
            return false;
        }

        return userTokens.stream().anyMatch(tokenStorage::contains);
    }

    public Cookie generateCookie() {
        String tokenValue = getTokenValue();
        tokenStorage.add(tokenValue);
        return new Cookie(TOKEN_KEY, tokenValue);
    }

    public Cookie generateRemovalCookie() {
        Cookie cookie = new Cookie(TOKEN_KEY, "");
        cookie.setMaxAge(0);
        return cookie;
    }

    public void clearUserTokens(List<String> userTokens) {
        tokenStorage.removeAll(userTokens);
    }

    public void addSalt(User user) {
        String salt = generateSalt();
        String saltedPassword = user.getPassword() + salt;
        String hashedSaltedPassword = hash(saltedPassword);

        user.setPassword(hashedSaltedPassword);
        user.setSalt(salt);
    }

    public boolean areValidCredentials(User user) {
        return userService.findOne(user.getUsername())
                .map(existingUser -> isPasswordValidForUser(user.getPassword(), existingUser))
                .orElse(false);
    }

    public boolean isUserRegistered(String username) {
        return userService.findOne(username).isPresent();
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
