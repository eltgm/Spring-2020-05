package ru.otus.exception;

public class NotCorrectException extends RuntimeException {
    public NotCorrectException(String message) {
        super(message);
    }
}
