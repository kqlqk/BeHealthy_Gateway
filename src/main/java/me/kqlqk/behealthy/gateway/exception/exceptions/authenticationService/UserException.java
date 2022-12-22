package me.kqlqk.behealthy.gateway.exception.exceptions.authenticationService;

import com.netflix.hystrix.exception.ExceptionNotWrappedByHystrix;

public class UserException extends RuntimeException implements ExceptionNotWrappedByHystrix {
    public UserException(String message) {
        super(message);
    }
}
