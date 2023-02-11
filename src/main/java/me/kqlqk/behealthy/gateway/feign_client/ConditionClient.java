package me.kqlqk.behealthy.gateway.feign_client;

import me.kqlqk.behealthy.gateway.dto.conditionService.*;
import org.springframework.cloud.openfeign.FallbackFactory;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.concurrent.TimeoutException;

@FeignClient(name = "conditionService", fallbackFactory = ConditionClient.Fallback.class)
public interface ConditionClient {
    @GetMapping("/api/v1/condition")
    UserConditionDTO getUserConditionByUserId(@RequestParam long userId);

    @PostMapping("/api/v1/condition")
    void createUserCondition(@RequestParam long userId, @RequestBody UserConditionDTO userConditionDTO);

    @PostMapping("/api/v1/condition/male/fatPercent")
    void createUserConditionWithoutFatPercentMale(@RequestParam long userId, @RequestBody UserConditionWithoutFatPercentMaleDTO userConditionWithoutFatPercentMaleDTO);

    @PostMapping("/api/v1/condition/female/fatPercent")
    void createUserConditionWithoutFatPercentFemale(@RequestParam long userId, @RequestBody UserConditionWithoutFatPercentFemaleDTO userConditionWithoutFatPercentFemaleDTO);

    @PutMapping("/api/v1/condition")
    void updateUserCondition(@RequestParam long userId, @RequestBody UserConditionDTO userConditionDTO);

    @GetMapping("/api/v1/kcals")
    DailyKcalsDTO getDailyKcalsByUserId(@RequestParam long userId);

    @GetMapping("/api/v1/food")
    List<DailyAteFoodDTO> getDailyAteFoods(@RequestParam long userId);

    @PostMapping("/api/v1/food")
    void addDailyAteFood(@RequestParam long userId, @RequestBody DailyAteFoodDTO dailyAteFoodDTO);

    @DeleteMapping("/api/v1/food")
    void deleteDailyAteFood(@RequestParam long productId, @RequestParam long userId);

    @GetMapping("/api/v1/photo")
    UserPhotoDTO getUserPhotoByDate(@RequestParam long userId, @RequestParam String date);

    @PostMapping("/api/v1/photo")
    void saveUserPhoto(@RequestParam long userId, @RequestBody UserPhotoDTO userPhotoDTO);

    @GetMapping("/api/v1/own/kcals")
    OwnDailyKcalsDTO getOwnDailyKcalsByUserId(@RequestParam long userId);

    @PostMapping("/api/v1/own/kcals")
    OwnDailyKcalsDTO createOwnDailyKcals(@RequestParam long userId, @RequestBody OwnDailyKcalsDTO ownDailyKcalsDTO);

    @PutMapping("/api/v1/own/kcals")
    OwnDailyKcalsDTO updateOwnDailyKcals(@RequestParam long userId, @RequestBody OwnDailyKcalsDTO ownDailyKcalsDTO);

    @Component
    class Fallback implements FallbackFactory<ConditionClient> {
        @Override
        public ConditionClient create(Throwable cause) {
            if (cause instanceof TimeoutException) {
                throw new RuntimeException("Service is unavailable");
            }

            if (cause instanceof RuntimeException) {
                throw (RuntimeException) cause;
            } else {
                throw new RuntimeException("Unhandled exception: " + cause.getMessage());
            }
        }
    }
}
