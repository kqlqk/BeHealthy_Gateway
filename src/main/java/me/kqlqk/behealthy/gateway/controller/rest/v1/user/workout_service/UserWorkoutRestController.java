package me.kqlqk.behealthy.gateway.controller.rest.v1.user.workout_service;

import me.kqlqk.behealthy.gateway.aop.CheckUserId;
import me.kqlqk.behealthy.gateway.dto.workout_service.AddUserWorkoutDTO;
import me.kqlqk.behealthy.gateway.dto.workout_service.GetUserWorkoutDTO;
import me.kqlqk.behealthy.gateway.feign_client.WorkoutClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/users/{id}/")
public class UserWorkoutRestController {
    private final WorkoutClient workoutClient;

    @Autowired
    public UserWorkoutRestController(WorkoutClient workoutClient) {
        this.workoutClient = workoutClient;
    }

    @CheckUserId
    @GetMapping("/workout/user")
    public List<GetUserWorkoutDTO> getUserWorkout(@PathVariable long id) {
        return workoutClient.getUserWorkout(id);
    }

    @CheckUserId
    @PostMapping("/workout/user")
    public ResponseEntity<?> addExercise(@PathVariable long id, @RequestBody AddUserWorkoutDTO addUserWorkoutDTO) {
        workoutClient.addExercise(id, addUserWorkoutDTO);

        return ResponseEntity.ok().build();
    }

    @CheckUserId
    @DeleteMapping("/workout/user")
    public ResponseEntity<?> removeExercise(@PathVariable long id, @RequestParam String exerciseName) {
        workoutClient.removeExercise(id, exerciseName);

        return ResponseEntity.ok().build();
    }
}
