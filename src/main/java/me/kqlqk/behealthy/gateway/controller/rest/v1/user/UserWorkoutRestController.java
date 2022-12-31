package me.kqlqk.behealthy.gateway.controller.rest.v1.user;

import com.fasterxml.jackson.annotation.JsonView;
import me.kqlqk.behealthy.gateway.aop.CheckUserId;
import me.kqlqk.behealthy.gateway.dto.workoutService.ExerciseDTO;
import me.kqlqk.behealthy.gateway.dto.workoutService.WorkoutInfoDTO;
import me.kqlqk.behealthy.gateway.exception.exceptions.workoutService.ExerciseNotFoundException;
import me.kqlqk.behealthy.gateway.feign_client.WorkoutClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api/v1/users/{id}")
public class UserWorkoutRestController {
    private final WorkoutClient workoutClient;

    @Autowired
    public UserWorkoutRestController(WorkoutClient workoutClient) {
        this.workoutClient = workoutClient;
    }


    @CheckUserId
    @GetMapping("/workout")
    @JsonView(ExerciseDTO.WithoutAlternativeId.class)
    public List<WorkoutInfoDTO> getWorkout(@PathVariable long id) {
        List<WorkoutInfoDTO> workoutInfoDTOS = workoutClient.getWorkout(id);

        for (WorkoutInfoDTO workoutInfoDTO : workoutInfoDTOS) {
            ExerciseDTO exerciseDTO = workoutInfoDTO.getExercise();
            exerciseDTO.setHasAlternative(exerciseDTO.getAlternativeId() != null);
        }

        return workoutInfoDTOS;
    }

    @CheckUserId
    @PostMapping("/workout")
    public ResponseEntity<?> createWorkout(@PathVariable long id, @RequestBody @Valid WorkoutInfoDTO workoutInfoDTO) {
        workoutClient.createWorkout(id, workoutInfoDTO);

        return ResponseEntity.ok().build();
    }

    @CheckUserId
    @PutMapping("/workout")
    public ResponseEntity<?> updateWorkout(@PathVariable long id, @RequestBody WorkoutInfoDTO workoutInfoDTO) {
        workoutClient.updateWorkout(id, workoutInfoDTO);

        return ResponseEntity.ok().build();
    }

    @CheckUserId
    @GetMapping("/exercises")
    @JsonView(ExerciseDTO.WithoutAlternativeId.class)
    public ResponseEntity<?> getExercisesByParams(@PathVariable long id,
                                                  @RequestParam(required = false) String name,
                                                  @RequestParam(required = false) String muscleGroup) {
        if (name == null && muscleGroup == null) {
            throw new ExerciseNotFoundException("Was not provided 'name' or 'muscleGroup'");
        }
        if (name != null && muscleGroup != null) {
            throw new ExerciseNotFoundException("Provide only 1 filter");
        }

        if (name != null) {
            ExerciseDTO exerciseDTO = workoutClient.getExerciseByName(name);

            exerciseDTO.setHasAlternative(exerciseDTO.getAlternativeId() != null);

            return ResponseEntity.ok(exerciseDTO);
        } else {
            List<ExerciseDTO> exerciseDTOS = workoutClient.getExercisesByMuscleGroup(muscleGroup);

            for (ExerciseDTO exerciseDTO : exerciseDTOS) {
                exerciseDTO.setHasAlternative(exerciseDTO.getAlternativeId() != null);
            }

            return ResponseEntity.ok(workoutClient.getExercisesByMuscleGroup(muscleGroup));
        }
    }

    @CheckUserId
    @PutMapping("/workout/alternative")
    public ResponseEntity<?> updateWorkoutWithAlternativeExercise(@PathVariable long id,
                                                                  @RequestParam String exercise) {
        workoutClient.updateWorkoutWithAlternativeExercise(id, exercise);

        return ResponseEntity.ok().build();
    }
}