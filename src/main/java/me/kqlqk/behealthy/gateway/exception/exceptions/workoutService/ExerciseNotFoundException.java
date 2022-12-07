package me.kqlqk.behealthy.gateway.exception.exceptions.workoutService;

public class ExerciseNotFoundException extends RuntimeException {
    public ExerciseNotFoundException(String message) {
        super(message);
    }
}
