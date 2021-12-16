package com.shop.service;

import com.github.javafaker.Faker;
import com.shop.entity.User;
import lombok.RequiredArgsConstructor;
import org.apache.commons.codec.digest.DigestUtils;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@RequiredArgsConstructor
public class SecurityService {

    public static final String PASSWORD_IS_NOT_VALID_MESSAGE = """
            Provided password does not match the requirements. Your password should:
            <br>- Be 5 or more characters
            <br>- Not be blank
            <br>- Not contain spaces
            """;

    private static final Faker FAKER = new Faker();

    private final List<String> tokenStorage = Collections.synchronizedList(new ArrayList<>());
    private final UserService userService;

    public boolean isLoggedIn(List<String> userTokens) {
        if (userTokens.isEmpty()) {
            return false;
        }

        return userTokens.stream().anyMatch(tokenStorage::contains);
    }

    public void clearUserTokens(List<String> userTokens) {
        tokenStorage.removeAll(userTokens);
    }

    public void addToken(String userToken) {
        tokenStorage.add(userToken);
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

}
