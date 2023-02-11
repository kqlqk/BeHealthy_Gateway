package me.kqlqk.behealthy.gateway.controller.rest.v1.user;

import com.fasterxml.jackson.annotation.JsonView;
import me.kqlqk.behealthy.gateway.aop.CheckUserId;
import me.kqlqk.behealthy.gateway.dto.conditionService.*;
import me.kqlqk.behealthy.gateway.feign_client.ConditionClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/api/v1/users/{id}")
public class UserConditionRestController {
    private final ConditionClient conditionClient;

    @Autowired
    public UserConditionRestController(ConditionClient conditionClient) {
        this.conditionClient = conditionClient;
    }

    @CheckUserId
    @GetMapping("/condition")
    @JsonView(UserConditionDTO.WithoutId.class)
    public UserConditionDTO getCurrentUserCondition(@PathVariable long id) {
        return conditionClient.getUserConditionByUserId(id);
    }

    @CheckUserId
    @PutMapping("/condition")
    public ResponseEntity<?> updateUserCondition(@PathVariable long id,
                                                 @RequestBody @Valid UserConditionDTO userConditionDTO) {
        conditionClient.updateUserCondition(id, userConditionDTO);

        return ResponseEntity.ok().build();
    }

    @CheckUserId
    @PostMapping("/condition")
    public ResponseEntity<?> createUserCondition(@PathVariable long id,
                                                 @RequestBody @Valid UserConditionDTO userConditionDTO) {
        conditionClient.createUserCondition(id, userConditionDTO);

        return ResponseEntity.ok().build();
    }

    @CheckUserId
    @PostMapping("/condition/male/fatPercent")
    public ResponseEntity<?> createUserConditionWithoutFatPercentMale(@PathVariable long id,
                                                                      @RequestBody @Valid UserConditionWithoutFatPercentMaleDTO userConditionWithoutFatPercentMaleDTO) {
        conditionClient.createUserConditionWithoutFatPercentMale(id, userConditionWithoutFatPercentMaleDTO);

        return ResponseEntity.ok().build();
    }

    @CheckUserId
    @PostMapping("/condition/female/fatPercent")
    public ResponseEntity<?> createUserConditionWithoutFatPercentFemale(@PathVariable long id,
                                                                        @RequestBody @Valid UserConditionWithoutFatPercentFemaleDTO userConditionWithoutFatPercentFemaleDTO) {
        conditionClient.createUserConditionWithoutFatPercentFemale(id, userConditionWithoutFatPercentFemaleDTO);

        return ResponseEntity.ok().build();
    }

    @CheckUserId
    @GetMapping("/kcals")
    public DailyKcalsDTO getCurrentUserDailyKcals(@PathVariable long id) {
        return conditionClient.getDailyKcalsByUserId(id);
    }

    @CheckUserId
    @GetMapping("/own/kcals")
    public OwnDailyKcalsDTO getOwnDailyKcalsByUserId(@PathVariable long id) {
        return conditionClient.getOwnDailyKcalsByUserId(id);
    }

    @CheckUserId
    @PostMapping("/own/kcals")
    public ResponseEntity<?> createOwnDailyKcals(@PathVariable long id, @RequestBody OwnDailyKcalsDTO ownDailyKcalsDTO) {
        conditionClient.createOwnDailyKcals(id, ownDailyKcalsDTO);

        return ResponseEntity.ok().build();
    }

    @CheckUserId
    @PutMapping("/own/kcals")
    public ResponseEntity<?> updateOwnDailyKcals(@PathVariable long id, @RequestBody OwnDailyKcalsDTO ownDailyKcalsDTO) {
        conditionClient.updateOwnDailyKcals(id, ownDailyKcalsDTO);

        return ResponseEntity.ok().build();
    }
}
