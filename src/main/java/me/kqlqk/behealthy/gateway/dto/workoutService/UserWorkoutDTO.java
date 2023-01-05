package me.kqlqk.behealthy.gateway.dto.workoutService;

import lombok.Data;

@Data
public class UserWorkoutDTO {
    private long id;
    private String exerciseName;
    private String muscleGroup;
    private int reps;
    private int sets;
    private int numberPerDay;
    private int day;
    private long userId;
}
