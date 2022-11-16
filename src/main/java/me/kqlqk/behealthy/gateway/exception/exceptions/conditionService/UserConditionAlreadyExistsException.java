package me.kqlqk.behealthy.gateway.exception.exceptions.conditionService;

public class UserConditionAlreadyExistsException extends RuntimeException {
    public UserConditionAlreadyExistsException(String message) {
        super(message);
    }
}
