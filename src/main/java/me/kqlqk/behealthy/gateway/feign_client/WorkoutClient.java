package me.kqlqk.behealthy.gateway.feign_client;

import me.kqlqk.behealthy.gateway.dto.workoutService.ExerciseDTO;
import me.kqlqk.behealthy.gateway.dto.workoutService.UserWorkoutDTO;
import me.kqlqk.behealthy.gateway.dto.workoutService.WorkoutInfoDTO;
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
    void updateWorkoutWithAlternativeExercise(@RequestParam long userId, @RequestParam String exerciseNameToChange);

    @GetMapping("/api/v1/user/workout")
    List<UserWorkoutDTO> getUserWorkout(@RequestParam long userId);

    @PostMapping("/api/v1/user/workout")
    void addExercise(@RequestParam long userId, @RequestBody UserWorkoutDTO userWorkoutDTO);

    @DeleteMapping("/api/v1/user/workout")
    void removeExercise(@RequestParam long userId, @RequestParam Long exerciseId);

    @Component
    class Fallback implements FallbackFactory<WorkoutClient> {
        @Override
        public WorkoutClient create(Throwable cause) {
            if (cause instanceof TimeoutException) {
                throw new RuntimeException("Service is unavailable");
            }
            if (cause instanceof RuntimeException) {
                throw (RuntimeException) cause;
            } else {
                throw new RuntimeException("Unhandled exception: " + cause.getMessage());
            }
        }
    }
}