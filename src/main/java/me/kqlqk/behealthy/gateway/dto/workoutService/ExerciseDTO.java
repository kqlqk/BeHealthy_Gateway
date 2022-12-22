package me.kqlqk.behealthy.gateway.dto.workoutService;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ExerciseDTO {
    private int id;
    private String name;
    private String description;
    private String muscleGroup;
}
