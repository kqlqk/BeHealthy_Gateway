package me.kqlqk.behealthy.gateway.dto.conditionService;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Pattern;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserConditionWithoutFatPercentMaleDTO {
    private long userId;
    private int age;
    private int height;
    private int weight;

    @Pattern(regexp = "MIN|AVG|MAX", message = "Please use valid intensity (MIN or AVG or MAX)")
    private String intensity;

    @Pattern(regexp = "LOSE|MAINTAIN|GAIN", message = "Please use valid goal (LOSE or MAINTAIN or GAIN)")
    private String goal;

    int fatFoldBetweenChestAndIlium;
    int fatFoldBetweenNavelAndLowerBelly;
    int fatFoldBetweenNippleAndArmpit;
    int fatFoldBetweenNippleAndUpperChest;
}
