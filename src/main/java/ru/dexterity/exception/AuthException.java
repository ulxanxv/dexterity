package ru.dexterity.exception;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public final class AuthException extends RuntimeException {

    private final AuthError code;
    private final String description;

    public enum AuthError {
        CREDENTIAL_EXIST,
        CREDENTIAL_INCORRECT,
    }

}