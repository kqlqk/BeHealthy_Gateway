package me.kqlqk.behealthy.gateway.dto.workoutService;

import com.fasterxml.jackson.annotation.JsonView;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ExerciseDTO {
    public interface WithoutAlternativeId {
    }

    @JsonView(WithoutAlternativeId.class)
    private int id;

    @JsonView(WithoutAlternativeId.class)
    private String name;

    @JsonView(WithoutAlternativeId.class)
    private String description;

    @JsonView(WithoutAlternativeId.class)
    private String muscleGroup;

    private Integer alternativeId;

    @JsonView(WithoutAlternativeId.class)
    private boolean hasAlternative;
}
