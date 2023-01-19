package me.kqlqk.behealthy.gateway.dto.conditionService;

import com.fasterxml.jackson.annotation.JsonView;
import lombok.Data;

@Data
public class DailyKcalsDTO {
    public interface WithoutUserId {
    }

    private long id;

    @JsonView(WithoutUserId.class)
    private int protein;

    @JsonView(WithoutUserId.class)
    private int fat;

    @JsonView(WithoutUserId.class)
    private int carb;
}
