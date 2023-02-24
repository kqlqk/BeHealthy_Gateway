package me.kqlqk.behealthy.gateway.dto.workout_service;

import lombok.Data;

import javax.validation.constraints.*;

@Data
public class AddUserWorkoutDTO {
    @Size(min = 1, max = 50, message = "ExerciseName should be between 1 and 50 characters")
    @NotBlank(message = "ExerciseName should be between 1 and 50 characters")
    private String exerciseName;

    @NotNull(message = "MuscleGroup cannot be null")
    private String muscleGroup;

    @Min(value = 0, message = "Rep should be between 0 and 1000")
    @Max(value = 1000, message = "Rep should be between 0 and 1000")
    private int rep;

    @Min(value = 1, message = "Set should be between 1 and 100")
    @Max(value = 100, message = "Set should be between 1 and 100")
    private int set;

    @Min(value = 1, message = "NumberPerDay should be between 1 and 100")
    @Max(value = 100, message = "NumberPerDay should be between 1 and 100")
    private int numberPerDay;

    @Min(value = 1, message = "Day should be between 1 and 7")
    @Max(value = 7, message = "Day should be between 1 and 7")
    private int day;
}
