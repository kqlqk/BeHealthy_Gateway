package me.kqlqk.behealthy.gateway.exception.exceptions.authenticationService;

import com.netflix.hystrix.exception.ExceptionNotWrappedByHystrix;

public class TokenNotFoundException extends RuntimeException implements ExceptionNotWrappedByHystrix {

    public TokenNotFoundException(String message) {
        super(message);
    }
}
