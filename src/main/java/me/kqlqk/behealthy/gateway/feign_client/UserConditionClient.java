package me.kqlqk.behealthy.gateway.feign_client;

import lombok.extern.slf4j.Slf4j;
import me.kqlqk.behealthy.gateway.dto.user_condition_service.*;
import me.kqlqk.behealthy.gateway.exception.RuntimeNotWrappedByHystrixException;
import org.springframework.cloud.openfeign.FallbackFactory;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@FeignClient(name = "userConditionService", fallbackFactory = UserConditionClient.Fallback.class)
public interface UserConditionClient {
    @GetMapping("/api/v1/condition")
    GetUserConditionDTO getUserConditionByUserId(@RequestParam long userId);

    @PostMapping("/api/v1/condition")
    void createUserCondition(@RequestParam long userId, @RequestBody AddUpdateUserConditionDTO addConditionDTO);

    @PutMapping("/api/v1/condition")
    void updateUserCondition(@RequestParam long userId, @RequestBody AddUpdateUserConditionDTO updateUserConditionDTO);

    @GetMapping("/api/v1/kcal")
    GetUserKcalDTO getUserKcalByUserId(@RequestParam long userId);

    @PostMapping("/api/v1/kcal")
    void createUserKcal(@RequestParam long userId, @RequestBody @Valid AddUpdateUserKcalDTO addUserKcalDTO);

    @PutMapping("/api/v1/kcal")
    void updateUserKcal(@RequestParam long userId, @RequestBody @Valid AddUpdateUserKcalDTO updateUserKcalDTO);

    @GetMapping("/api/v1/food")
    List<GetDailyAteFoodDTO> getDailyAteFoods(@RequestParam long userId);

    @PostMapping("/api/v1/food")
    void saveDailyAteFood(@RequestParam long userId, @RequestBody AddDailyAteFoodDTO addDailyAteFoodDTO);

    @DeleteMapping("/api/v1/food")
    void deleteDailyAteFood(@RequestParam long productId, @RequestParam long userId);

    @GetMapping("/api/v1/photo")
    GetEncodedPhoto getEncodedPhotoByDate(@RequestParam long userId, @RequestParam String date);

    @GetMapping("/api/v1/photo/all")
    List<FullUserPhotoDTO> getAllPhotosAndFiles(@RequestParam long userId);

    @PostMapping("/api/v1/photo")
    void saveUserPhoto(@RequestParam long userId, @RequestBody AddUserPhotoDTO addUserPhotoDTO);

    @Component
    @Slf4j
    class Fallback implements FallbackFactory<UserConditionClient> {
        @Override
        public UserConditionClient create(Throwable cause) {
            log.warn("Something went wrong: ", cause);

            throw new RuntimeNotWrappedByHystrixException("Service is unavailable");
        }
    }
}
