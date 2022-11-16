package me.kqlqk.behealthy.gateway.exception.exceptions.authenticationService;

public class TokenException extends RuntimeException {
    public TokenException(String message) {
        super(message);
    }
}
