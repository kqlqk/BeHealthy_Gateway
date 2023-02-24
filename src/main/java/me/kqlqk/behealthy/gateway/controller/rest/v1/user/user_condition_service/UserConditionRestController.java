package me.kqlqk.behealthy.gateway.controller.rest.v1.user.user_condition_service;

import me.kqlqk.behealthy.gateway.aop.CheckUserId;
import me.kqlqk.behealthy.gateway.dto.user_condition_service.AddUpdateUserConditionDTO;
import me.kqlqk.behealthy.gateway.dto.user_condition_service.GetUserConditionDTO;
import me.kqlqk.behealthy.gateway.feign_client.UserConditionClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/api/v1/users/{id}")
public class UserConditionRestController {
    private final UserConditionClient userConditionClient;

    @Autowired
    public UserConditionRestController(UserConditionClient userConditionClient) {
        this.userConditionClient = userConditionClient;
    }

    @CheckUserId
    @GetMapping("/condition")
    public GetUserConditionDTO getCurrentUserCondition(@PathVariable long id) {
        return userConditionClient.getUserConditionByUserId(id);
    }

    @CheckUserId
    @PutMapping("/condition")
    public ResponseEntity<?> updateUserCondition(@PathVariable long id,
                                                 @RequestBody @Valid AddUpdateUserConditionDTO updateUserConditionDTO) {
        userConditionClient.updateUserCondition(id, updateUserConditionDTO);

        return ResponseEntity.ok().build();
    }

    @CheckUserId
    @PostMapping("/condition")
    public ResponseEntity<?> createUserCondition(@PathVariable long id,
                                                 @RequestBody @Valid AddUpdateUserConditionDTO createUserConditionDTO) {
        userConditionClient.createUserCondition(id, createUserConditionDTO);

        return ResponseEntity.ok().build();
    }
}
