package me.kqlqk.behealthy.gateway.controller.rest.v1.user;

import com.fasterxml.jackson.annotation.JsonView;
import me.kqlqk.behealthy.gateway.dto.workoutService.WorkoutInfoDTO;
import me.kqlqk.behealthy.gateway.exception.exceptions.UserException;
import me.kqlqk.behealthy.gateway.feign_client.WorkoutClient;
import me.kqlqk.behealthy.gateway.service.AuthenticationClientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/users/{id}")
public class UserWorkoutRestController {
    private final AuthenticationClientService authenticationClientService;
    private final WorkoutClient workoutClient;

    @Autowired
    public UserWorkoutRestController(AuthenticationClientService authenticationClientService, WorkoutClient workoutClient) {
        this.authenticationClientService = authenticationClientService;
        this.workoutClient = workoutClient;
    }


    @GetMapping("/workout")
    @JsonView(WorkoutInfoDTO.WithoutUserIdAndWorkoutsPerWeekView.class)
    public List<WorkoutInfoDTO> getWorkout(@PathVariable long id) {
        if (id != authenticationClientService.getUserFromContext().getId()) {
            throw new UserException("Id = " + id + " is not your, please, use id = " +
                    authenticationClientService.getUserFromContext().getId());
        }

        return workoutClient.getWorkout(id);
    }

    @PostMapping("/workout")
    public ResponseEntity<?> createWorkout(@PathVariable long id, @RequestBody WorkoutInfoDTO workoutInfoDTO) {
        if (id != authenticationClientService.getUserFromContext().getId()) {
            throw new UserException("Id = " + id + " is not your, please, use id = " +
                    authenticationClientService.getUserFromContext().getId());
        }

        workoutInfoDTO.setUserId(id);
        workoutClient.createWorkout(workoutInfoDTO);

        return ResponseEntity.ok().build();
    }

    @PutMapping("/workout")
    public ResponseEntity<?> updateWorkout(@PathVariable long id, @RequestBody WorkoutInfoDTO workoutInfoDTO) {
        if (id != authenticationClientService.getUserFromContext().getId()) {
            throw new UserException("Id = " + id + " is not your, please, use id = " +
                    authenticationClientService.getUserFromContext().getId());
        }

        workoutInfoDTO.setUserId(id);
        workoutClient.updateWorkout(workoutInfoDTO);

        return ResponseEntity.ok().build();
    }
}