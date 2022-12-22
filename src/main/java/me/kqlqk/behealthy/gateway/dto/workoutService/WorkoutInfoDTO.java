package me.kqlqk.behealthy.gateway.dto.workoutService;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class WorkoutInfoDTO {
    private long id;
    private int workoutDay;
    private int numberPerDay;
    private ExerciseDTO exercise;
    private int reps;
    private int sets;

    @Min(value = 2, message = "workoutsPerWeek should be valid (2 or 3 or 4)")
    @Max(value = 4, message = "workoutsPerWeek should be valid (2 or 3 or 4)")
    private int workoutsPerWeek;
}
