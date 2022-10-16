package me.kqlqk.behealthy.gateway.feign_client;

import me.kqlqk.behealthy.gateway.dto.kcalCounterService.DailyFoodDTO;
import me.kqlqk.behealthy.gateway.dto.kcalCounterService.KcalsInfoDTO;
import me.kqlqk.behealthy.gateway.dto.kcalCounterService.UserConditionDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@FeignClient(name = "KcalsCounterService")
public interface KcalsCounterClient {
    @GetMapping("/api/v1/condition")
    UserConditionDTO getUserConditionByUserId(@RequestParam long userId);

    @PostMapping("/api/v1/condition")
    void createUserCondition(@RequestBody UserConditionDTO userConditionDTO);

    @GetMapping("/api/v1/kcals")
    KcalsInfoDTO getKcalsInfoByUserId(@RequestParam long userId);

    @PutMapping("/api/v1/condition")
    void updateUserCondition(@RequestParam long userId, @RequestBody UserConditionDTO userConditionDTO);

    @GetMapping("/api/v1/daily_food")
    List<DailyFoodDTO> getDailyFoodsForUser(@RequestParam long userId);

    @PostMapping("/api/v1/daily_food")
    void addDailyFoodForUser(@RequestBody DailyFoodDTO dailyFoodDTO);

    @DeleteMapping("/api/v1/daily_food")
    void deleteDailyFoodFromUser(@RequestParam long productId);
}
