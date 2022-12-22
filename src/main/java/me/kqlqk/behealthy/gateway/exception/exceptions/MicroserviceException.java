package me.kqlqk.behealthy.gateway.exception.exceptions;

import com.netflix.hystrix.exception.ExceptionNotWrappedByHystrix;

public class MicroserviceException extends RuntimeException implements ExceptionNotWrappedByHystrix {
    public MicroserviceException(String message) {
        super(message);
    }
}
