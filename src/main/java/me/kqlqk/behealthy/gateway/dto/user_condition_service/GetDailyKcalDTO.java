package me.kqlqk.behealthy.gateway.dto.user_condition_service;

import lombok.Data;

@Data
public class GetDailyKcalDTO {
    private int protein;
    private int fat;
    private int carb;
}
