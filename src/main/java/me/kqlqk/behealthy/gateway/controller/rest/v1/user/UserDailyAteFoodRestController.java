package me.kqlqk.behealthy.gateway.controller.rest.v1.user;

import me.kqlqk.behealthy.gateway.aop.CheckUserId;
import me.kqlqk.behealthy.gateway.dto.conditionService.DailyAteFoodDTO;
import me.kqlqk.behealthy.gateway.feign_client.ConditionClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/users/{id}")
public class UserDailyAteFoodRestController {
    private final ConditionClient conditionClient;

    @Autowired
    public UserDailyAteFoodRestController(ConditionClient conditionClient) {
        this.conditionClient = conditionClient;
    }

    @CheckUserId
    @GetMapping("/food")
    public List<DailyAteFoodDTO> getDailyAteFoods(@PathVariable long id) {
        return conditionClient.getDailyAteFoods(id);
    }

    @CheckUserId
    @PostMapping("/food")
    public ResponseEntity<?> addDailyAteFoods(@PathVariable long id, @RequestBody DailyAteFoodDTO dailyAteFoodDTO) {
        conditionClient.addDailyAteFood(id, dailyAteFoodDTO);

        return ResponseEntity.ok().build();
    }

    @CheckUserId
    @DeleteMapping("/food")
    public ResponseEntity<?> deleteDailyAteFood(@PathVariable long id, @RequestParam long productId) {
        conditionClient.deleteDailyAteFood(productId, id);

        return ResponseEntity.ok().build();
    }
}
