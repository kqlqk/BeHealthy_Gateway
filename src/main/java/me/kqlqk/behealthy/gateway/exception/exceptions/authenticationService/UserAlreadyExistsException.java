package me.kqlqk.behealthy.gateway.exception.exceptions.authenticationService;

public class UserAlreadyExistsException extends RuntimeException {
    public UserAlreadyExistsException(String message) {
        super(message);
    }
}
