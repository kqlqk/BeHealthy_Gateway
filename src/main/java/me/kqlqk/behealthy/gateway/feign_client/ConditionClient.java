package me.kqlqk.behealthy.gateway.feign_client;

import me.kqlqk.behealthy.gateway.dto.kcalCounterService.DailyFoodDTO;
import me.kqlqk.behealthy.gateway.dto.kcalCounterService.KcalsInfoDTO;
import me.kqlqk.behealthy.gateway.dto.kcalCounterService.UserConditionDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@FeignClient(name = "conditionService")
public interface ConditionClient {
    @GetMapping("/api/v1/condition")
    UserConditionDTO getUserConditionByUserId(@RequestParam long userId);

    @PostMapping("/api/v1/condition")
    void createUserCondition(@RequestParam long userId, @RequestBody UserConditionDTO userConditionDTO);

    @PutMapping("/api/v1/condition")
    void updateUserCondition(@RequestParam long userId, @RequestBody UserConditionDTO userConditionDTO);

    @GetMapping("/api/v1/kcals")
    KcalsInfoDTO getKcalsInfoByUserId(@RequestParam long userId);

    @GetMapping("/api/v1/food")
    List<DailyFoodDTO> getDailyFoodsForUser(@RequestParam long userId);

    @PostMapping("/api/v1/food")
    void addDailyFoodForUser(@RequestParam long userId, @RequestBody DailyFoodDTO dailyFoodDTO);

    @DeleteMapping("/api/v1/food")
    void deleteDailyFoodFromUser(@RequestParam long productId);
}
