package me.kqlqk.behealthy.gateway.exception.exceptions.workoutService;

public class WorkoutNotFoundException extends RuntimeException {
    public WorkoutNotFoundException(String message) {
        super(message);
    }
}
