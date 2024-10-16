package com.api.Library.dtos;

public class LoginResponse {
    private String token;
    private long expiresIn;

    // Getter for token
    public String getToken() {
        return token;
    }

    // Setter for token
    public LoginResponse setToken(String token) {
        this.token = token;
        return this; // returning this for method chaining if needed
    }

    // Getter for expiresIn
    public long getExpiresIn() {
        return expiresIn;
    }

    // Setter for expiresIn
    public LoginResponse setExpiresIn(long expiresIn) {
        this.expiresIn = expiresIn;
        return this; // returning this for method chaining if needed
    }
}
