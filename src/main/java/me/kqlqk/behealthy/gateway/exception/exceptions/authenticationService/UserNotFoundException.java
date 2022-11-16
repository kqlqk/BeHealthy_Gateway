package me.kqlqk.behealthy.gateway.exception.exceptions.authenticationService;

public class UserNotFoundException extends RuntimeException {
    public UserNotFoundException(String message) {
        super(message);
    }
}
