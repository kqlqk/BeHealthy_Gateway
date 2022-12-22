package me.kqlqk.behealthy.gateway.exception.exceptions.authenticationService;

import com.netflix.hystrix.exception.ExceptionNotWrappedByHystrix;

public class UserAlreadyExistsException extends RuntimeException implements ExceptionNotWrappedByHystrix {
    public UserAlreadyExistsException(String message) {
        super(message);
    }
}
