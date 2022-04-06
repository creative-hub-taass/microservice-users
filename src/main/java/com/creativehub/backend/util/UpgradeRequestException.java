package com.creativehub.backend.util;

public class UpgradeRequestException extends Exception {

    public enum UpgradeRequestExceptionType {
        NOT_FOUND, ALREADY_ACCEPTED, ALREADY_REJECTED
    }
    public UpgradeRequestException() {}

    public UpgradeRequestException(String message) {
        super(message);
    }
}
