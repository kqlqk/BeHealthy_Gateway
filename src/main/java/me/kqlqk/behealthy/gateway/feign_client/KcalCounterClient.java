package me.kqlqk.behealthy.gateway.feign_client;

import me.kqlqk.behealthy.gateway.dto.kcalCounterService.KcalsInfoDTO;
import me.kqlqk.behealthy.gateway.dto.kcalCounterService.UserConditionDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(name = "KcalCounterService")
public interface KcalCounterClient {
    @GetMapping("/api/v1/condition")
    UserConditionDTO getUserConditionByUserId(@RequestParam long userId);

    @PostMapping("/api/v1/condition")
    void createUserCondition(@RequestBody UserConditionDTO userConditionDTO);

    @GetMapping("/api/v1/kcals")
    KcalsInfoDTO getKcalsInfoByUserId(@RequestParam long userId);
}
