package me.kqlqk.behealthy.gateway.dto.workoutService;

import com.fasterxml.jackson.annotation.JsonView;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Pattern;

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

    @Pattern(regexp = "TWO|THREE|FOUR", message = "Please use valid data (TWO or THREE or FOUR)")
    @JsonView(WithoutUserIdView.class)
    private String workoutsPerWeek;
}
