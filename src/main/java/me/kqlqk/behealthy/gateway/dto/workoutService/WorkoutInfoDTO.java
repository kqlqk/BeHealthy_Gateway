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
    public interface WithoutUserIdView {
    }

    @JsonView(WithoutUserIdView.class)
    private long id;

    private long userId;

    @JsonView(WithoutUserIdView.class)
    private int workoutDay;

    @JsonView(WithoutUserIdView.class)
    private int numberPerDay;

    @JsonView(WithoutUserIdView.class)
    private ExerciseDTO exercise;

    @Min(value = 2, message = "workoutsPerWeek should be valid (2 or 3 or 4)")
    @Max(value = 4, message = "workoutsPerWeek should be valid (2 or 3 or 4)")
    @JsonView(WithoutUserIdView.class)
    private int workoutsPerWeek;
}
