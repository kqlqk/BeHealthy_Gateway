package me.kqlqk.behealthy.gateway.dto.conditionService;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class OwnDailyKcalsDTO {
    private long id;
    private int protein;
    private int fat;
    private int carb;
    private long userId;
    private boolean inPriority;
}
