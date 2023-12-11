package ru.skypro.homework.exception;

public class PhotoOnDatabaseIsAbsentException extends RuntimeException{
    public PhotoOnDatabaseIsAbsentException() {
        System.out.println("Ошибка: фото отсутствует в базе данных");
    }

    public PhotoOnDatabaseIsAbsentException(String message) {
        super(message);
    }

    public PhotoOnDatabaseIsAbsentException(String message, Throwable cause) {
        super(message, cause);
    }

    public PhotoOnDatabaseIsAbsentException(Throwable cause) {
        super(cause);
    }

    public PhotoOnDatabaseIsAbsentException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
