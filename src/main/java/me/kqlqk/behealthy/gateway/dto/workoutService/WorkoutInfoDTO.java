package me.kqlqk.behealthy.gateway.dto.workoutService;

import com.fasterxml.jackson.annotation.JsonView;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class WorkoutInfoDTO {
    public interface WithoutUserIdAndWorkoutsPerWeekView {
    }

    @JsonView(WithoutUserIdAndWorkoutsPerWeekView.class)
    private long id;

    private long userId;

    @JsonView(WithoutUserIdAndWorkoutsPerWeekView.class)
    private int workoutDay;

    @JsonView(WithoutUserIdAndWorkoutsPerWeekView.class)
    private int numberPerDay;

    @JsonView(WithoutUserIdAndWorkoutsPerWeekView.class)
    private ExerciseDTO exercise;

    private String workoutsPerWeek;
}
