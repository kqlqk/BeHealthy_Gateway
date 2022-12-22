package me.kqlqk.behealthy.gateway.exception.exceptions.authenticationService;

import com.netflix.hystrix.exception.ExceptionNotWrappedByHystrix;

public class TokenException extends RuntimeException implements ExceptionNotWrappedByHystrix {

    public TokenException(String message) {
        super(message);
    }
}
