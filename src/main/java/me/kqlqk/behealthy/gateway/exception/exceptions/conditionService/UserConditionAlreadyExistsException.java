package me.kqlqk.behealthy.gateway.exception.exceptions.conditionService;

import com.netflix.hystrix.exception.ExceptionNotWrappedByHystrix;

public class UserConditionAlreadyExistsException extends RuntimeException implements ExceptionNotWrappedByHystrix {
    public UserConditionAlreadyExistsException(String message) {
        super(message);
    }
}
