package me.kqlqk.behealthy.gateway.controller.rest.v1.user;

import me.kqlqk.behealthy.gateway.aop.CheckUserId;
import me.kqlqk.behealthy.gateway.dto.workoutService.UserWorkoutDTO;
import me.kqlqk.behealthy.gateway.exception.exceptions.workoutService.ExerciseNotFoundException;
import me.kqlqk.behealthy.gateway.feign_client.WorkoutClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/users/{id}/own")
public class UserOwnWorkoutRestController {
    private final WorkoutClient workoutClient;

    @Autowired
    public UserOwnWorkoutRestController(WorkoutClient workoutClient) {
        this.workoutClient = workoutClient;
    }

    @CheckUserId
    @GetMapping("/workout")
    public List<UserWorkoutDTO> getUserWorkout(@PathVariable long id) {
        return workoutClient.getUserWorkout(id);
    }

    @CheckUserId
    @PostMapping("/workout")
    public ResponseEntity<?> addExercise(@PathVariable long id, @RequestBody UserWorkoutDTO userWorkoutDTO) {
        workoutClient.addExercise(id, userWorkoutDTO);

        return ResponseEntity.ok().build();
    }

    @CheckUserId
    @DeleteMapping("/workout")
    public ResponseEntity<?> removeExercise(@PathVariable long id,
                                            @RequestParam(required = false) Long exerciseId,
                                            @RequestParam(required = false) String exerciseName) {
        if (exerciseId == null && exerciseName == null) {
            throw new ExerciseNotFoundException("Provide 'exerciseId' or 'exerciseName'");
        } else if (exerciseId != null && exerciseName != null) {
            throw new ExerciseNotFoundException("Provide only 1 filter");
        }

        if (exerciseId != null) {
            workoutClient.removeExerciseByExerciseId(id, exerciseId);
        } else {
            workoutClient.removeExerciseByExerciseName(id, exerciseName);
        }

        return ResponseEntity.ok().build();
    }
}
