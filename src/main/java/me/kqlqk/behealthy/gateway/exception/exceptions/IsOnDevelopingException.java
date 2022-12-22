package me.kqlqk.behealthy.gateway.exception.exceptions;

import com.netflix.hystrix.exception.ExceptionNotWrappedByHystrix;

public class IsOnDevelopingException extends RuntimeException implements ExceptionNotWrappedByHystrix {
    public IsOnDevelopingException(String message) {
        super(message);
    }
}
