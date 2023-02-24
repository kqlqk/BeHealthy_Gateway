package me.kqlqk.behealthy.gateway.dto.workout_service;

import lombok.Data;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;

@Data
public class AddUpdateWorkoutInfoDTO {
    @Min(value = 1, message = "WorkoutsPerWeek should be between 1 and 5")
    @Max(value = 5, message = "WorkoutsPerWeek should be between 1 and 5")
    private int workoutsPerWeek;
}
