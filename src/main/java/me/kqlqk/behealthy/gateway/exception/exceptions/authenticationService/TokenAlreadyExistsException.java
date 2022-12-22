package me.kqlqk.behealthy.gateway.exception.exceptions.authenticationService;

import com.netflix.hystrix.exception.ExceptionNotWrappedByHystrix;

public class TokenAlreadyExistsException extends RuntimeException implements ExceptionNotWrappedByHystrix {

    public TokenAlreadyExistsException(String message) {
        super(message);
    }
}
