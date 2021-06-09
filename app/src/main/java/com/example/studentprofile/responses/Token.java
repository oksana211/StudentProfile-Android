package com.example.studentprofile.responses;

public class Token {

    private static Token instance;
    public String value;

    private Token(String value) {
        // Этот код эмулирует медленную инициализацию.
//        try {
//            Thread.sleep(3000);
//        } catch (InterruptedException ex) {
//            ex.printStackTrace();
//        }
        this.value = value;
    }

    public static Token getInstance() {
        return instance;
    }

    public static Token setInstance(String value) {
        instance = new Token(value);
        return instance;
    }

    @Override
    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("Bearer ");
        String token = this.value;
        stringBuilder.append(token.substring(1, token.length()-1));
        return  stringBuilder.toString();
    }
}
