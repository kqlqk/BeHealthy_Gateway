package me.kqlqk.behealthy.gateway.controller.rest.v1.user;

import me.kqlqk.behealthy.gateway.aop.CheckUserId;
import me.kqlqk.behealthy.gateway.dto.conditionService.KcalsInfoDTO;
import me.kqlqk.behealthy.gateway.dto.conditionService.UserConditionDTO;
import me.kqlqk.behealthy.gateway.feign_client.ConditionClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
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
    public UserConditionDTO getCurrentUserCondition(@PathVariable long id) {
        return conditionClient.getUserConditionByUserId(id);
    }

    @CheckUserId
    @PutMapping("/condition")
    public ResponseEntity<?> updateUserCondition(@PathVariable long id,
                                                 @RequestBody @Valid UserConditionDTO userConditionDTO,
                                                 HttpServletResponse response) {
        conditionClient.updateUserCondition(id, userConditionDTO);

        if (response.getStatus() != 200) {
            return ResponseEntity.status(response.getStatus()).build();
        }

        return ResponseEntity.ok().build();
    }

    @CheckUserId
    @PostMapping("/condition")
    public ResponseEntity<?> createUserCondition(@PathVariable long id,
                                                 @RequestBody @Valid UserConditionDTO userConditionDTO,
                                                 HttpServletResponse response) {
        conditionClient.createUserCondition(id, userConditionDTO);

        if (response.getStatus() != 200) {
            return ResponseEntity.status(response.getStatus()).build();
        }

        return ResponseEntity.ok().build();
    }

    @CheckUserId
    @GetMapping("/kcals")
    public KcalsInfoDTO getCurrentUserKcalsInfo(@PathVariable long id) {
        return conditionClient.getKcalsInfoByUserId(id);
    }
}
