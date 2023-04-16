package me.kqlqk.behealthy.gateway.controller.rest.v1.user.user_condition_service;

import me.kqlqk.behealthy.gateway.aop.CheckUserId;
import me.kqlqk.behealthy.gateway.dto.user_condition_service.AddUpdateDailyAteFoodDTO;
import me.kqlqk.behealthy.gateway.feign_client.UserConditionClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/users/{id}")
public class DailyAteFoodRestController {
    private final UserConditionClient userConditionClient;

    @Autowired
    public DailyAteFoodRestController(UserConditionClient userConditionClient) {
        this.userConditionClient = userConditionClient;
    }

    @CheckUserId
    @GetMapping("/food")
    public ResponseEntity<?> getAllDailyAteFoods(@PathVariable long id, @RequestParam(required = false) String productName) {
        if (productName != null) {
            return ResponseEntity.ok(userConditionClient.getSpecifiedDailyAteFoods(productName, id));
        }

        return ResponseEntity.ok(userConditionClient.getAllDailyAteFoods(id));
    }

    @CheckUserId
    @PostMapping("/food")
    public ResponseEntity<?> addDailyAteFoods(@PathVariable long id, @RequestBody AddUpdateDailyAteFoodDTO addDailyAteFoodDTO) {
        userConditionClient.saveDailyAteFood(id, addDailyAteFoodDTO);

        return ResponseEntity.ok().build();
    }

    @CheckUserId
    @PutMapping("/food")
    public ResponseEntity<?> updateDailyAteFoods(@PathVariable long id, @RequestBody AddUpdateDailyAteFoodDTO updateDailyAteFoodDTO) {
        userConditionClient.updateDailyAteFood(id, updateDailyAteFoodDTO);

        return ResponseEntity.ok().build();
    }

    @CheckUserId
    @DeleteMapping("/food")
    public ResponseEntity<?> deleteDailyAteFood(@PathVariable long id, @RequestParam String productName) {
        userConditionClient.deleteDailyAteFood(productName, id);

        return ResponseEntity.ok().build();
    }
}
