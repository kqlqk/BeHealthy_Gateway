package me.kqlqk.behealthy.gateway.exception.exceptions.authenticationService;

public class TokenAlreadyExistsException extends RuntimeException {

    public TokenAlreadyExistsException(String message) {
        super(message);
    }
}
