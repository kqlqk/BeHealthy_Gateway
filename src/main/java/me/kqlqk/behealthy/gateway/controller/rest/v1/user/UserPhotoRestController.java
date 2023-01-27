package me.kqlqk.behealthy.gateway.controller.rest.v1.user;

import com.fasterxml.jackson.annotation.JsonView;
import me.kqlqk.behealthy.gateway.aop.CheckUserId;
import me.kqlqk.behealthy.gateway.dto.conditionService.UserPhotoDTO;
import me.kqlqk.behealthy.gateway.feign_client.ConditionClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/users/{id}")
public class UserPhotoRestController {
    private final ConditionClient conditionClient;

    @Autowired
    public UserPhotoRestController(ConditionClient conditionClient) {
        this.conditionClient = conditionClient;
    }


    @CheckUserId
    @GetMapping("/photo")
    @JsonView(UserPhotoDTO.WithoutUserId.class)
    public UserPhotoDTO getUserPhotoByDate(@PathVariable long id, @RequestParam String date) {
        return conditionClient.getUserPhotoByDate(id, date);
    }

    @CheckUserId
    @PostMapping("/photo")
    public void saveUserPhoto(@PathVariable long id, @RequestBody UserPhotoDTO userPhotoDTO) {
        conditionClient.saveUserPhoto(id, userPhotoDTO);
    }
}
