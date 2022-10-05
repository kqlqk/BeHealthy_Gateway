package me.kqlqk.behealthy.gateway.dto.kcalCounterService;

import lombok.Data;

@Data
public class KcalsInfoDTO {
    private long id;
    private short protein;
    private short fat;
    private short carb;
}
