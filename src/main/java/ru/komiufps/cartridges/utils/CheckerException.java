package ru.komiufps.cartridges.utils;

public class CheckerException extends Exception {
    private String message;

    public CheckerException(String message) {
        super(message);
    }
}
