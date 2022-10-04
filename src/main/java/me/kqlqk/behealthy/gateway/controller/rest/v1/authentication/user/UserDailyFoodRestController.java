package me.kqlqk.behealthy.gateway.controller.rest.v1.authentication.user;

import com.fasterxml.jackson.annotation.JsonView;
import me.kqlqk.behealthy.gateway.dto.kcalCounterService.DailyFoodDTO;
import me.kqlqk.behealthy.gateway.exception.exceptions.UserException;
import me.kqlqk.behealthy.gateway.feign_client.KcalCounterClient;
import me.kqlqk.behealthy.gateway.service.AuthenticationClientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api/v1/users/{id}")
public class UserDailyFoodRestController {
    private final KcalCounterClient kcalCounterClient;
    private final AuthenticationClientService authenticationClientService;

    @Autowired
    public UserDailyFoodRestController(KcalCounterClient kcalCounterClient,
                                       AuthenticationClientService authenticationClientService) {
        this.kcalCounterClient = kcalCounterClient;
        this.authenticationClientService = authenticationClientService;
    }

    @GetMapping("/daily_food")
    @JsonView(DailyFoodDTO.WithoutUserIdView.class)
    public List<DailyFoodDTO> getDailyFoodsForUser(@PathVariable long id) {
        if (id != authenticationClientService.getUserFromContext().getId()) {
            throw new UserException("Id = " + id + " is not your, please, use id = " +
                    authenticationClientService.getUserFromContext().getId());
        }

        return kcalCounterClient.getDailyFoodsForUser(id);
    }

    @PostMapping("/daily_food")
    public ResponseEntity<?> addDailyFoodsForUser(@PathVariable long id,
                                                  @RequestBody @Valid DailyFoodDTO dailyFoodDTO,
                                                  HttpServletResponse response) {
        if (id != authenticationClientService.getUserFromContext().getId()) {
            throw new UserException("Id = " + id + " is not your, please, use id = " +
                    authenticationClientService.getUserFromContext().getId());
        }

        dailyFoodDTO.setUserId(id);
        kcalCounterClient.addDailyFoodForUser(dailyFoodDTO);

        if (response.getStatus() != 200) {
            return ResponseEntity.status(response.getStatus()).build();
        }

        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/daily_food")
    public ResponseEntity<?> deleteDailyFoodsForUser(@PathVariable long id,
                                                     @RequestParam long productId,
                                                     HttpServletResponse response) {
        if (id != authenticationClientService.getUserFromContext().getId()) {
            throw new UserException("Id = " + id + " is not your, please, use id = " +
                    authenticationClientService.getUserFromContext().getId());
        }

        kcalCounterClient.getDailyFoodsForUser(id)
                .stream()
                .filter(product -> product.getId() == productId)
                .findAny()
                .orElseThrow(() -> new UserException("Daily food with id = " + productId + " not found for user with userId = " + id));
        kcalCounterClient.deleteDailyFoodFromUser(productId);

        if (response.getStatus() != 200) {
            return ResponseEntity.status(response.getStatus()).build();
        }

        return ResponseEntity.ok().build();
    }
}
