package com.se3.ase.data.model;

/**
 * Data class that captures user information for logged in users retrieved from LoginRepository
 */
public class LoggedInUser {

    private String userId;
    private String displayName;
    private String token;
    private String email;


    public LoggedInUser(String userId, String displayName, String token, String email) {
        this.userId = userId;
        this.displayName = displayName;
        this.token = token;
        this.email = email;
    }

    public String getUserId() {
        return userId;
    }

    public String getDisplayName() {
        return displayName;
    }

    public String getToken() {
        return token;
    }

    public String getEmail() {
        return email;
    }
}