package me.kqlqk.behealthy.gateway.controller.rest.v1.user;

import me.kqlqk.behealthy.gateway.aop.CheckUserId;
import me.kqlqk.behealthy.gateway.dto.conditionService.DailyFoodDTO;
import me.kqlqk.behealthy.gateway.feign_client.ConditionClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api/v1/users/{id}")
public class UserDailyFoodRestController {
    private final ConditionClient conditionClient;

    @Autowired
    public UserDailyFoodRestController(ConditionClient conditionClient) {
        this.conditionClient = conditionClient;
    }

    @CheckUserId
    @GetMapping("/food")
    public List<DailyFoodDTO> getDailyFoodsForUser(@PathVariable long id) {
        return conditionClient.getDailyFoodsForUser(id);
    }

    @CheckUserId
    @PostMapping("/food")
    public ResponseEntity<?> addDailyFoodsForUser(@PathVariable long id,
                                                  @RequestBody @Valid DailyFoodDTO dailyFoodDTO) {
        conditionClient.addDailyFoodForUser(id, dailyFoodDTO);

        return ResponseEntity.ok().build();
    }

    @CheckUserId
    @DeleteMapping("/food")
    public ResponseEntity<?> deleteDailyFoodsForUser(@PathVariable long id,
                                                     @RequestParam long productId) {
        conditionClient.deleteDailyFoodFromUser(productId, id);

        return ResponseEntity.ok().build();
    }
}
