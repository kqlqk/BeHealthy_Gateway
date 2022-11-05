package me.kqlqk.behealthy.gateway.feign_client;

import me.kqlqk.behealthy.gateway.dto.workoutService.WorkoutInfoDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@FeignClient(name = "workoutService")
public interface WorkoutClient {

    @GetMapping("/api/v1/workout")
    List<WorkoutInfoDTO> getWorkout(@RequestParam("userId") long userId);

    @PostMapping("/api/v1/workout")
    void createWorkout(@RequestBody WorkoutInfoDTO workoutInfoDTO);

    @PutMapping("/api/v1/workout")
    void updateWorkout(@RequestBody WorkoutInfoDTO workoutInfoDTO);
}