package me.kqlqk.behealthy.gateway.feign_client;

import lombok.extern.slf4j.Slf4j;
import me.kqlqk.behealthy.gateway.dto.workout_service.*;
import me.kqlqk.behealthy.gateway.exception.RuntimeNotWrappedByHystrixException;
import org.springframework.cloud.openfeign.FallbackFactory;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@FeignClient(name = "workoutService", fallbackFactory = WorkoutClient.Fallback.class)
public interface WorkoutClient {

    @GetMapping("/api/v1/workout")
    List<GetWorkoutInfoDTO> getWorkoutInfos(@RequestParam long userId);

    @PostMapping("/api/v1/workout")
    void createWorkoutInfos(@RequestParam long userId, @RequestBody AddUpdateWorkoutInfoDTO addWorkoutInfoDTO);

    @PutMapping("/api/v1/workout")
    void updateWorkoutInfos(@RequestParam long userId, @RequestBody AddUpdateWorkoutInfoDTO updateWorkoutInfoDTO);

    @GetMapping("/api/v1/exercises")
    GetExerciseDTO getExerciseByName(@RequestParam String name);

    @GetMapping("/api/v1/exercises")
    List<GetExerciseDTO> getExercisesByMuscleGroup(@RequestParam String muscleGroup);

    @PutMapping("/api/v1/workout/alternative")
    void updateWorkoutWithAlternativeExercise(@RequestParam long userId, @RequestParam String exerciseName);

    @GetMapping("/api/v1/workout/user")
    List<GetUserWorkoutDTO> getUserWorkout(@RequestParam long userId);

    @PostMapping("/api/v1/workout/user")
    void addExercise(@RequestParam long userId, @RequestBody AddUserWorkoutDTO addUserWorkoutDTO);

    @DeleteMapping("/api/v1/workout/user")
    void removeExercise(@RequestParam long userId, @RequestParam String exerciseName);

    @Component
    @Slf4j
    class Fallback implements FallbackFactory<WorkoutClient> {
        @Override
        public WorkoutClient create(Throwable cause) {
            log.warn("Something went wrong: ", cause);

            throw new RuntimeNotWrappedByHystrixException("Service is unavailable");
        }
    }
}