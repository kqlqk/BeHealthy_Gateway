package me.kqlqk.behealthy.gateway.exception;

import com.netflix.hystrix.exception.ExceptionNotWrappedByHystrix;

public class RuntimeNotWrappedByHystrixException extends RuntimeException implements ExceptionNotWrappedByHystrix {
    public RuntimeNotWrappedByHystrixException(String message) {
        super(message);
    }
}
