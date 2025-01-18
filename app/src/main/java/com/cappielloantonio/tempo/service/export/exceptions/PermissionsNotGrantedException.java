package com.cappielloantonio.tempo.service.export.exceptions;

public class PermissionsNotGrantedException extends RuntimeException {
    public PermissionsNotGrantedException(String message) {
        super(message);
    }
}
