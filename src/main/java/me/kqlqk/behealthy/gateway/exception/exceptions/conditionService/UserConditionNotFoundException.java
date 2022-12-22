package me.kqlqk.behealthy.gateway.exception.exceptions.conditionService;

import com.netflix.hystrix.exception.ExceptionNotWrappedByHystrix;

public class UserConditionNotFoundException extends RuntimeException implements ExceptionNotWrappedByHystrix {
    public UserConditionNotFoundException(String message) {
        super(message);
    }
}
