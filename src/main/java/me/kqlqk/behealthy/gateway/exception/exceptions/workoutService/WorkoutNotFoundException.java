package me.kqlqk.behealthy.gateway.exception.exceptions.workoutService;

import com.netflix.hystrix.exception.ExceptionNotWrappedByHystrix;

public class WorkoutNotFoundException extends RuntimeException implements ExceptionNotWrappedByHystrix {
    public WorkoutNotFoundException(String message) {
        super(message);
    }
}
