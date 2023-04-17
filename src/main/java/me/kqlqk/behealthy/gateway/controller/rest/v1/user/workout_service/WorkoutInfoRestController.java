package me.kqlqk.behealthy.gateway.controller.rest.v1.user.workout_service;

import me.kqlqk.behealthy.gateway.aop.CheckUserId;
import me.kqlqk.behealthy.gateway.dto.workout_service.AddUpdateWorkoutInfoDTO;
import me.kqlqk.behealthy.gateway.dto.workout_service.GetWorkoutInfoDTO;
import me.kqlqk.behealthy.gateway.feign_client.WorkoutClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/users/{id}")
public class WorkoutInfoRestController {
    private final WorkoutClient workoutClient;

    @Autowired
    public WorkoutInfoRestController(WorkoutClient workoutClient) {
        this.workoutClient = workoutClient;
    }


    @CheckUserId
    @GetMapping("/workout")
    public List<GetWorkoutInfoDTO> getWorkout(@PathVariable long id) {
        return workoutClient.getWorkoutInfos(id);
    }

    @CheckUserId
    @PostMapping("/workout")
    public ResponseEntity<?> createWorkout(@PathVariable long id, @RequestBody AddUpdateWorkoutInfoDTO addWorkoutInfoDTO) {
        workoutClient.createWorkoutInfos(id, addWorkoutInfoDTO);

        return ResponseEntity.ok().build();
    }

    @CheckUserId
    @PutMapping("/workout")
    public ResponseEntity<?> updateWorkout(@PathVariable long id, @RequestBody AddUpdateWorkoutInfoDTO updateWorkoutInfoDTO) {
        workoutClient.updateWorkoutInfos(id, updateWorkoutInfoDTO);

        return ResponseEntity.ok().build();
    }

    @CheckUserId
    @GetMapping("/exercises")
    public ResponseEntity<?> getExercisesByParams(@PathVariable long id,
                                                  @RequestParam(required = false) String name,
                                                  @RequestParam(required = false) String muscleGroup) {
        if (name == null && muscleGroup == null) {
            throw new RuntimeException("Was not provided 'name' or 'muscleGroup' filter");
        }
        if (name != null && muscleGroup != null) {
            throw new RuntimeException("Provide only 1 filter");
        }

        if (name != null) {
            return ResponseEntity.ok(workoutClient.getExerciseByName(name));
        }
        else {
            return ResponseEntity.ok(workoutClient.getExercisesByMuscleGroup(muscleGroup));
        }
    }

    @CheckUserId
    @PutMapping("/workout/alternative")
    public ResponseEntity<?> updateWorkoutWithAlternativeExercise(@PathVariable long id,
                                                                  @RequestParam String exerciseName) {
        workoutClient.updateWorkoutWithAlternativeExercise(id, exerciseName);

        return ResponseEntity.ok().build();
    }
}