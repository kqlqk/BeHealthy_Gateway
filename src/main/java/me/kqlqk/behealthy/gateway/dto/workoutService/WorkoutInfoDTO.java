package me.kqlqk.behealthy.gateway.dto.workoutService;

import com.fasterxml.jackson.annotation.JsonView;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class WorkoutInfoDTO {
    @JsonView(ExerciseDTO.WithoutAlternativeId.class)
    private long id;

    @JsonView(ExerciseDTO.WithoutAlternativeId.class)
    private int day;

    @JsonView(ExerciseDTO.WithoutAlternativeId.class)
    private int numberPerDay;

    @JsonView(ExerciseDTO.WithoutAlternativeId.class)
    private ExerciseDTO exercise;

    @JsonView(ExerciseDTO.WithoutAlternativeId.class)
    private int reps;

    @JsonView(ExerciseDTO.WithoutAlternativeId.class)
    private int sets;

    @JsonView(ExerciseDTO.WithoutAlternativeId.class)
    private int workoutsPerWeek;
}
