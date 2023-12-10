package ru.skypro.homework.exception;

public class PhotoOnPcIsAbsentException extends RuntimeException{
    public PhotoOnPcIsAbsentException() {
    }

    public PhotoOnPcIsAbsentException(String message) {
        super(message);
    }

    public PhotoOnPcIsAbsentException(String message, Throwable cause) {
        super(message, cause);
    }

    public PhotoOnPcIsAbsentException(Throwable cause) {
        super(cause);
    }

    public PhotoOnPcIsAbsentException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
