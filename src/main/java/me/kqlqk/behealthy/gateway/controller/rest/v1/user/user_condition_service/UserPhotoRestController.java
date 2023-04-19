package me.kqlqk.behealthy.gateway.controller.rest.v1.user.user_condition_service;

import me.kqlqk.behealthy.gateway.aop.CheckUserId;
import me.kqlqk.behealthy.gateway.dto.user_condition_service.AddUserPhotoDTO;
import me.kqlqk.behealthy.gateway.dto.user_condition_service.FullUserPhotoDTO;
import me.kqlqk.behealthy.gateway.dto.user_condition_service.GetEncodedPhoto;
import me.kqlqk.behealthy.gateway.feign_client.UserConditionClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/users/{id}")
public class UserPhotoRestController {
    private final UserConditionClient userConditionClient;

    @Autowired
    public UserPhotoRestController(UserConditionClient userConditionClient) {
        this.userConditionClient = userConditionClient;
    }


    @CheckUserId
    @GetMapping("/photo")
    public GetEncodedPhoto getEncodedPhotoByDate(@PathVariable long id, @RequestParam String date) {
        return userConditionClient.getEncodedPhotoByDate(id, date);
    }

    @CheckUserId
    @GetMapping("/photo/all")
    public List<FullUserPhotoDTO> getAllPhotosAndFiles(@PathVariable long id) {
        return userConditionClient.getAllPhotosAndFiles(id);
    }

    @CheckUserId
    @PostMapping("/photo")
    public void saveUserPhoto(@PathVariable long id, @RequestBody AddUserPhotoDTO addUserPhotoDTO) {
        userConditionClient.saveUserPhoto(id, addUserPhotoDTO);
    }

    @CheckUserId
    @DeleteMapping("/photo")
    public void deleteUserPhoto(@PathVariable long id, @RequestParam(required = false) String date) {
        userConditionClient.deleteUserPhoto(id, date);
    }
}
