package me.kqlqk.behealthy.gateway.dto.workoutService;

import com.fasterxml.jackson.annotation.JsonView;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ExerciseDTO {
    @JsonView(WorkoutInfoDTO.WithoutUserIdView.class)
    private int id;

    @JsonView(WorkoutInfoDTO.WithoutUserIdView.class)
    private String name;

    @JsonView(WorkoutInfoDTO.WithoutUserIdView.class)
    private String description;

    @JsonView(WorkoutInfoDTO.WithoutUserIdView.class)
    private String muscleGroup;
}
