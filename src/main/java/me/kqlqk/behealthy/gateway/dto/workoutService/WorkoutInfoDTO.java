package me.kqlqk.behealthy.gateway.dto.workoutService;

import com.fasterxml.jackson.annotation.JsonView;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class WorkoutInfoDTO {
    @JsonView(ExerciseDTO.WithoutAlternativeId.class)
    private long id;

    @JsonView(ExerciseDTO.WithoutAlternativeId.class)
    private int workoutDay;

    @JsonView(ExerciseDTO.WithoutAlternativeId.class)
    private int numberPerDay;

    @JsonView(ExerciseDTO.WithoutAlternativeId.class)
    private ExerciseDTO exercise;

    @JsonView(ExerciseDTO.WithoutAlternativeId.class)
    private int reps;

    @JsonView(ExerciseDTO.WithoutAlternativeId.class)
    private int sets;

    @Min(value = 2, message = "workoutsPerWeek should be valid (2 or 3 or 4)")
    @Max(value = 4, message = "workoutsPerWeek should be valid (2 or 3 or 4)")
    @JsonView(ExerciseDTO.WithoutAlternativeId.class)
    private int workoutsPerWeek;
}
