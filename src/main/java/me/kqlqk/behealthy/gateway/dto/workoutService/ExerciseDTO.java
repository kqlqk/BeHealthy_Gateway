package me.kqlqk.behealthy.gateway.dto.workoutService;

import com.fasterxml.jackson.annotation.JsonView;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ExerciseDTO {
    @JsonView(WorkoutInfoDTO.WithoutUserIdAndWorkoutsPerWeekView.class)
    private int id;

    @JsonView(WorkoutInfoDTO.WithoutUserIdAndWorkoutsPerWeekView.class)
    private String name;

    @JsonView(WorkoutInfoDTO.WithoutUserIdAndWorkoutsPerWeekView.class)
    private String description;

    @JsonView(WorkoutInfoDTO.WithoutUserIdAndWorkoutsPerWeekView.class)
    private String muscleGroup;
}
