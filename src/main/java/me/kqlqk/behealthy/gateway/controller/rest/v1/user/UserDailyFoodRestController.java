package me.kqlqk.behealthy.gateway.controller.rest.v1.user;

import me.kqlqk.behealthy.gateway.dto.kcalCounterService.DailyFoodDTO;
import me.kqlqk.behealthy.gateway.exception.exceptions.authenticationService.UserException;
import me.kqlqk.behealthy.gateway.feign_client.ConditionClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
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

    @GetMapping("/food")
    public List<DailyFoodDTO> getDailyFoodsForUser(@PathVariable long id) {
        return conditionClient.getDailyFoodsForUser(id);
    }

    @PostMapping("/food")
    public ResponseEntity<?> addDailyFoodsForUser(@PathVariable long id,
                                                  @RequestBody @Valid DailyFoodDTO dailyFoodDTO,
                                                  HttpServletResponse response) {
        conditionClient.addDailyFoodForUser(id, dailyFoodDTO);

        if (response.getStatus() != 200) {
            return ResponseEntity.status(response.getStatus()).build();
        }

        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/food")
    public ResponseEntity<?> deleteDailyFoodsForUser(@PathVariable long id,
                                                     @RequestParam long productId,
                                                     HttpServletResponse response) {
        conditionClient.getDailyFoodsForUser(id)
                .stream()
                .filter(product -> product.getId() == productId)
                .findAny()
                .orElseThrow(() -> new UserException("Daily food with id = " + productId + " not found for user with userId = " + id));
        conditionClient.deleteDailyFoodFromUser(productId);

        if (response.getStatus() != 200) {
            return ResponseEntity.status(response.getStatus()).build();
        }

        return ResponseEntity.ok().build();
    }
}
