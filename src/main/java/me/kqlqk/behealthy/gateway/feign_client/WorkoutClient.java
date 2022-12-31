package me.kqlqk.behealthy.gateway.feign_client;

import me.kqlqk.behealthy.gateway.dto.workoutService.ExerciseDTO;
import me.kqlqk.behealthy.gateway.dto.workoutService.WorkoutInfoDTO;
import me.kqlqk.behealthy.gateway.exception.exceptions.MicroserviceException;
import org.springframework.cloud.openfeign.FallbackFactory;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.concurrent.TimeoutException;

@FeignClient(name = "workoutService", fallbackFactory = WorkoutClient.Fallback.class)
public interface WorkoutClient {

    @GetMapping("/api/v1/workout")
    List<WorkoutInfoDTO> getWorkout(@RequestParam long userId);

    @PostMapping("/api/v1/workout")
    void createWorkout(@RequestParam long userId, @RequestBody WorkoutInfoDTO workoutInfoDTO);

    @PutMapping("/api/v1/workout")
    void updateWorkout(@RequestParam long userId, @RequestBody WorkoutInfoDTO workoutInfoDTO);

    @GetMapping("/api/v1/exercises")
    ExerciseDTO getExerciseByName(@RequestParam String name);

    @GetMapping("/api/v1/exercises")
    List<ExerciseDTO> getExercisesByMuscleGroup(@RequestParam String muscleGroup);

    @PutMapping("/api/v1/workout/alternative")
    void updateWorkoutWithAlternativeExercise(@RequestParam long userId,
                                              @RequestParam String exerciseNameToChange);

    @Component
    class Fallback implements FallbackFactory<WorkoutClient> {
        @Override
        public WorkoutClient create(Throwable cause) {
            if (cause instanceof TimeoutException) {
                throw new MicroserviceException("Service is unavailable");
            }

            if (cause instanceof RuntimeException) {
                throw (RuntimeException) cause;
            } else {
                throw new RuntimeException("Unhandled exception: " + cause.getMessage());
            }
        }
    }
}