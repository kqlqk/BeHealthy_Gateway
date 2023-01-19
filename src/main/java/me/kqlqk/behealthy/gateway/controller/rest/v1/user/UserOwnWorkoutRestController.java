package me.kqlqk.behealthy.gateway.controller.rest.v1.user;

import me.kqlqk.behealthy.gateway.aop.CheckUserId;
import me.kqlqk.behealthy.gateway.dto.workoutService.UserWorkoutDTO;
import me.kqlqk.behealthy.gateway.feign_client.WorkoutClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/users/{id}/")
public class UserOwnWorkoutRestController {
    private final WorkoutClient workoutClient;

    @Autowired
    public UserOwnWorkoutRestController(WorkoutClient workoutClient) {
        this.workoutClient = workoutClient;
    }

    @CheckUserId
    @GetMapping("/workout/own")
    public List<UserWorkoutDTO> getUserWorkout(@PathVariable long id) {
        return workoutClient.getUserWorkout(id);
    }

    @CheckUserId
    @PostMapping("/workout/own")
    public ResponseEntity<?> addExercise(@PathVariable long id, @RequestBody UserWorkoutDTO userWorkoutDTO) {
        workoutClient.addExercise(id, userWorkoutDTO);

        return ResponseEntity.ok().build();
    }

    @CheckUserId
    @DeleteMapping("/workout/own")
    public ResponseEntity<?> removeExercise(@PathVariable long id, @RequestParam Long exerciseId) {
        workoutClient.removeExercise(id, exerciseId);

        return ResponseEntity.ok().build();
    }
}
