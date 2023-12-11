package ru.skypro.homework.exception;

public class PasswordIsNotMatchException extends RuntimeException{
    public PasswordIsNotMatchException() {
    }

    public PasswordIsNotMatchException(String message) {
        super(message);
    }

    public PasswordIsNotMatchException(String message, Throwable cause) {
        super(message, cause);
    }

    public PasswordIsNotMatchException(Throwable cause) {
        super(cause);
    }

    public PasswordIsNotMatchException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}