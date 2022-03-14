package com.se3.ase.data.model;
/**
 * Data class that captures user information for a register user retrieved from RegistrationRepository
 */
public class RegisteredUser {
    private String username;
    private String email;
    private String userId;

    public RegisteredUser(String username, String email,String userId) {
        this.username = username;
        this.email = email;
        this.userId = userId;
    }

    public String getUsername() {
        return username;
    }

    public String getEmail() {
        return email;
    }

    public String getUserId() {
        return userId;
    }
}
