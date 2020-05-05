package com.example.patientpal.services;

public final class TokenHandler {
    private TokenHandler() {}

    private static String token = "";

    public static void setToken(String newToken) {
        if (newToken != null) {
            token = "Bearer "+newToken;
        }
    }

    public static String getToken() {
        return token;
    }

    public static void removeToken(){
        token="";
    }
}