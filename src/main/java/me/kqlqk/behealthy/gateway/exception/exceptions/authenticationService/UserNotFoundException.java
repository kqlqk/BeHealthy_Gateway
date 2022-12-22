package me.kqlqk.behealthy.gateway.exception.exceptions.authenticationService;

import com.netflix.hystrix.exception.ExceptionNotWrappedByHystrix;

public class UserNotFoundException extends RuntimeException implements ExceptionNotWrappedByHystrix {
    public UserNotFoundException(String message) {
        super(message);
    }
}
