package me.kqlqk.behealthy.gateway.exception.exceptions.authenticationService;

public class TokenNotFoundException extends RuntimeException {

    public TokenNotFoundException(String message) {
        super(message);
    }
}
