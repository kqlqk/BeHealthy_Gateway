package me.kqlqk.behealthy.gateway.dto.conditionService;

import lombok.Data;

@Data
public class DailyAteFoodDTO {
    private long id;
    private String name;
    private double weight;
    private double kcals;
    private double proteins;
    private double fats;
    private double carbs;
}
