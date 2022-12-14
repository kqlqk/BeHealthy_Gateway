package me.kqlqk.behealthy.gateway.exception.exceptions;

public class MicroserviceException extends RuntimeException {
    public MicroserviceException(String message) {
        super(message);
    }
}
