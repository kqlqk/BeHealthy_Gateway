package me.kqlqk.behealthy.gateway.dto.conditionService;

import lombok.Data;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.Pattern;

@Data
public class UserConditionDTO {
    private long id;

    @Pattern(regexp = "MALE|FEMALE", message = "Please use valid gender (MALE or FEMALE)")
    private String gender;

    @Min(value = 15, message = "Please use valid age (between 15 and 60)")
    @Max(value = 60, message = "Please use valid age (between 15 and 60)")
    private byte age;

    @Min(value = 150, message = "Please use valid height (between 150 and 200)")
    @Max(value = 200, message = "Please use valid height (between 150 and 200)")
    private short height;

    @Min(value = 40, message = "Please use valid weight (between 40 and 90)")
    @Max(value = 150, message = "Please use valid weight (between 40 and 90)")
    private short weight;

    @Pattern(regexp = "MIN|AVG|MAX", message = "Please use valid intensity (MIN or AVG or MAX)")
    private String intensity;

    @Pattern(regexp = "LOSE|MAINTAIN|GAIN", message = "Please use valid goal (LOSE or MAINTAIN or GAIN)")
    private String goal;

    @Min(value = 1, message = "Please use valid fatPercent (between 1 and 50)")
    @Max(value = 50, message = "Please use valid fatPercent (between 1 and 50)")
    private double fatPercent;
}
