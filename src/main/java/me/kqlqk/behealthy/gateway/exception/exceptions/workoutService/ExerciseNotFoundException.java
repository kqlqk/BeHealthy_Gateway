package me.kqlqk.behealthy.gateway.exception.exceptions.workoutService;

import com.netflix.hystrix.exception.ExceptionNotWrappedByHystrix;

public class ExerciseNotFoundException extends RuntimeException implements ExceptionNotWrappedByHystrix {
    public ExerciseNotFoundException(String message) {
        super(message);
    }
}
