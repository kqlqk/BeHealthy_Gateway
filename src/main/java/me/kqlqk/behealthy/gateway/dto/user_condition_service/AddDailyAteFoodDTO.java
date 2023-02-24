package me.kqlqk.behealthy.gateway.dto.user_condition_service;

import lombok.Data;

@Data
public class AddDailyAteFoodDTO {
    private String name;
    private double weight;
    private int protein;
    private int fat;
    private int carb;
}
