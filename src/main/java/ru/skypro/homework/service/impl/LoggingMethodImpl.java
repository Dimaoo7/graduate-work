package ru.skypro.homework.service.impl;

import ru.skypro.homework.service.LoggingMethod;

public class LoggingMethodImpl implements LoggingMethod {
    public static String getMethodName() {
        StackTraceElement[] stackTrace = Thread.currentThread().getStackTrace();
        return stackTrace[2].getMethodName();
    }
}
