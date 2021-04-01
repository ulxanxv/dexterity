package ru.dexterity.auth;

public final class AuthException extends RuntimeException {

    private final AuthError code;
    private final String description;

    public AuthException(AuthError code, String description) {
        this.code = code;
        this.description = description;
    }

    public AuthError getCode() {
        return code;
    }

    public String getDescription() {
        return description;
    }

    public static enum AuthError {
        CREDENTIAL_EXIST,
        CREDENTIAL_INCORRECT,
    }

}
