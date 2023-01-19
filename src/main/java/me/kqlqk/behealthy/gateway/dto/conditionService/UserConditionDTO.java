package me.kqlqk.behealthy.gateway.dto.conditionService;

import com.fasterxml.jackson.annotation.JsonView;
import lombok.Data;

import javax.validation.constraints.Pattern;

@Data
public class UserConditionDTO {
    public interface WithoutId {
    }

    private long id;

    @JsonView(WithoutId.class)
    @Pattern(regexp = "MALE|FEMALE", message = "Please use valid gender (MALE or FEMALE)")
    private String gender;

    @JsonView(WithoutId.class)
    private byte age;

    @JsonView(WithoutId.class)
    private short height;

    @JsonView(WithoutId.class)
    private short weight;

    @JsonView(WithoutId.class)
    @Pattern(regexp = "MIN|AVG|MAX", message = "Please use valid intensity (MIN or AVG or MAX)")
    private String intensity;

    @JsonView(WithoutId.class)
    @Pattern(regexp = "LOSE|MAINTAIN|GAIN", message = "Please use valid goal (LOSE or MAINTAIN or GAIN)")
    private String goal;

    @JsonView(WithoutId.class)
    private double fatPercent;
}
