package me.kqlqk.behealthy.gateway.controller.rest.v1.user.user_condition_service;

import me.kqlqk.behealthy.gateway.aop.CheckUserId;
import me.kqlqk.behealthy.gateway.dto.user_condition_service.AddUpdateUserKcalDTO;
import me.kqlqk.behealthy.gateway.dto.user_condition_service.GetUserKcalDTO;
import me.kqlqk.behealthy.gateway.feign_client.UserConditionClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/api/v1/users/{id}")
public class UserKcalRestController {
    private final UserConditionClient userConditionClient;

    @Autowired
    public UserKcalRestController(UserConditionClient userConditionClient) {
        this.userConditionClient = userConditionClient;
    }

    @CheckUserId
    @GetMapping("/kcal")
    public GetUserKcalDTO getCurrentUserCondition(@PathVariable long id) {
        return userConditionClient.getUserKcalByUserId(id);
    }

    @CheckUserId
    @PostMapping("/kcal")
    public ResponseEntity<?> createUserCondition(@PathVariable long id,
                                                 @RequestBody @Valid AddUpdateUserKcalDTO addUserKcalDTO) {
        userConditionClient.createUserKcal(id, addUserKcalDTO);

        return ResponseEntity.ok().build();
    }

    @CheckUserId
    @PutMapping("/kcal")
    public ResponseEntity<?> updateUserCondition(@PathVariable long id,
                                                 @RequestBody @Valid AddUpdateUserKcalDTO updateUserKcalDTO) {
        userConditionClient.updateUserKcal(id, updateUserKcalDTO);

        return ResponseEntity.ok().build();
    }
}
